package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.product.SkuProductFeignClient;
import com.atguigu.gmall.feign.search.SearchFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.starter.cache.annotation.GmallCache;
import com.atguigu.starter.cache.service.CacheOpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author xjrstart
 * @Date 2022-08-26-21:44
 */
@Slf4j
@Service
public class SkuDetailServiceImpl implements SkuDetailService {

    @Autowired
    SkuProductFeignClient skuDetailFeignClient;
    // 可配置的线程池，可自动注入
    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    CacheOpsService cacheOpsService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SearchFeignClient searchFeignClient;

    /*
    * #{#params}  表达式中的params代表方法中的所有参数列表
    *   [0]  索引0代表方法中参数的第1个
    * */
    @GmallCache(cacheKey = SysRedisConst.SKU_INFO_PREFIX + "#{#params[0]}",
                bloomName = SysRedisConst.BLOOM_SKUID,
                bloomValue = "#{#params[0]}",
                lockName = SysRedisConst.LOCK_SKU_DETAIL+"#{#params[0]}",
                ttl = 60*60*24*7
    )
    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
        SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
        return fromRpc;
    }

    @Override
    public void updateHotScore(Long skuId) {
        Long increment = redisTemplate.opsForValue().increment(SysRedisConst.SKU_HOTSCORE_PREFIX + skuId);
        if (increment % 100 == 0) {
            searchFeignClient.updateHotScore(skuId,increment);
        }
    }


    public SkuDetailTo getSkuDetailFromRpc(Long skuId) {
        SkuDetailTo detailTo = new SkuDetailTo();

        CountDownLatch downLatch = new CountDownLatch(6);

        //CompletableFuture.runAsync()// CompletableFuture<Void>  启动一个下面不用它返回结果的异步任务
        //CompletableFuture.supplyAsync()//CompletableFuture<U>  启动一个下面用它返回结果的异步任务

        // 查基本信息
        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            Result<SkuInfo> result = skuDetailFeignClient.getSkuInfo(skuId);
            SkuInfo skuInfo = result.getData();
            detailTo.setSkuInfo(skuInfo);
            return skuInfo;
        },executor);

        //查商品图片信息
        CompletableFuture<Void> imageFuture = skuInfoFuture.thenAcceptAsync(skuInfo-> {
            if (skuInfo != null){
            Result<List<SkuImage>> skuImages = skuDetailFeignClient.getSkuImages(skuId);
            skuInfo.setSkuImageList(skuImages.getData());
            }
        }, executor);
        // 查商品的实时价格
        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() -> {
            Result<BigDecimal> price = skuDetailFeignClient.getSku1010Price(skuId);
            detailTo.setPrice(price.getData());
        }, executor);


        // 查销售属性组合
        CompletableFuture<Void> saleAttrFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if (skuInfo != null) {
            Long spuId = skuInfo.getSpuId();
            Result<List<SpuSaleAttr>> saleAttrValues = skuDetailFeignClient.getSkuSaleattrvalues(spuId, skuId);
            detailTo.setSpuSaleAttrList(saleAttrValues.getData());
            }
        }, executor);


        // 查sku组合
        CompletableFuture<Void> skuValueFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if (skuInfo != null){
            Result<String> skuValueJson = skuDetailFeignClient.getSkuValueJson(skuInfo.getSpuId());
            detailTo.setValuesSkuJson(skuValueJson.getData());
            }
        }, executor);


        // 查分类
        CompletableFuture<Void> categoryFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if (skuInfo != null){
            Result<CategoryViewTo> categoryView = skuDetailFeignClient.getCategoryView(skuInfo.getCategory3Id());
            detailTo.setCategoryView(categoryView.getData());
            }
        }, executor);


        CompletableFuture
                .allOf(imageFuture,priceFuture,saleAttrFuture,skuValueFuture,categoryFuture)
                .join();

        return detailTo;
    }

    public SkuDetailTo getSkuDetailWithCache(Long skuId) {
        String cacheKey = SysRedisConst.SKU_INFO_PREFIX + skuId;
        //先查缓存
        SkuDetailTo cacheData = cacheOpsService.getCacheData(cacheKey,SkuDetailTo.class);
        //判断
        if (cacheData == null){
            //缓存中没有,先问布隆是否有这个商品
            boolean contain = cacheOpsService.bloomContains(skuId);
            if (!contain){
                //布隆说没有  一定没有
                return null;
            }
            // 布隆说有 有可能有 需要回源查数据
            boolean lock = cacheOpsService.tryLock(skuId); // 为当前商品添加一个属于自己的锁
            if (lock){
                //获取锁成功
                SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
                //数据放到缓存
                cacheOpsService.saveData(cacheKey,fromRpc);
                //解锁操作
                cacheOpsService.unlock(skuId);
                return fromRpc;
            }
            // 说明没获取到锁
            try {
                Thread.sleep(1000);
                return cacheOpsService.getCacheData(cacheKey,SkuDetailTo.class);
            } catch (InterruptedException e) {
            }
        }

        return cacheData;
    }


}


























