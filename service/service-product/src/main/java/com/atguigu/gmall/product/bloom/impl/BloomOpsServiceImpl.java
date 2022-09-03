package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-09-01-18:37
 */
@Service
public class BloomOpsServiceImpl implements BloomOpsService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    SkuInfoService skuInfoService;


    @Override
    public void rebuildBloom(String bloomName, BloomDataQueryService dataQueryService) {
        RBloomFilter<Object> oldbloomFilter = redissonClient.getBloomFilter(bloomName);

        // 先准备一个心得布隆过滤器，所有东西都初始化好
        String newBloomName = bloomName + "_new";
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(newBloomName);
        // 拿到所有的商品id
        //List<Long> allSkuId = skuInfoService.findAllSkuId();
        List list = dataQueryService.queryData(); // 动态决定
        // 初始化心得布隆
        bloomFilter.tryInit(5000000,0.00001);
        for (Object skuId : list){
            bloomFilter.add(skuId);
        }
        // 新布隆准备就绪
            // ob  bb  nb  冒泡

        // 两个交换: nb 编程 ob  大数据量的删除会导致redis卡死
        oldbloomFilter.rename("bbbb_bloom"); // 老布隆下线
        bloomFilter.rename(bloomName); // 西安布隆上线

        // 删除老布隆和中间交换层   deleteAsync异步删除
        oldbloomFilter.deleteAsync();
        redissonClient.getBloomFilter("bbbb_bloom").deleteAsync();
    }
}

































