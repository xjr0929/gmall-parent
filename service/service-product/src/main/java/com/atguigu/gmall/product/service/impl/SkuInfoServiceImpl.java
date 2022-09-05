package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.feign.search.SearchFeignClient;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author 86136
* @description 针对表【sku_info(库存单元表)】的数据库操作Service实现
* @createDate 2022-08-23 18:42:09
*/
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
    implements SkuInfoService {

    @Autowired
    SkuImageService skuImageService;

    @Autowired
    SkuAttrValueService skuAttrValueService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    SkuInfoMapper skuInfoMapper;

    @Autowired
    BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    SpuSaleAttrService spuSaleAttrService;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    BaseTrademarkService baseTrademarkService;

    @Autowired
    SearchFeignClient searchFeignClient;

    @Override
    public void saveSkuInfo(SkuInfo info) {

        // sku基本信息保存到sku_info
        save(info);
        Long skuId = info.getId();
        // sku图片信息保存骚sku_image
        for (SkuImage skuImage : info.getSkuImageList()){
            skuImage.setSkuId(skuId);
        }
        //批量
        skuImageService.saveBatch(info.getSkuImageList());

        // sku的品台属性名和值的关系保存到sku_attr_value
        List<SkuAttrValue> attrValueList = info.getSkuAttrValueList();
        for (SkuAttrValue attrValue : attrValueList) {
            attrValue.setSkuId(skuId);
        }
        skuAttrValueService.saveBatch(attrValueList);

        // sku的销售属性名和值的关闭保存到sku_sale_attr_value
        List<SkuSaleAttrValue> saleAttrValueList = info.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue saleAttrValue : saleAttrValueList) {
            saleAttrValue.setSkuId(skuId);
            saleAttrValue.setSpuId(info.getSpuId());
        }
        skuSaleAttrValueService.saveBatch(saleAttrValueList);
        //把skuId放到布隆过滤器中

        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        filter.add(skuId);
    }

    // 下架
    @Override
    public void cancelSale(Long skuId) {
        // 该数据库中sku_info表中skuId的is_sale字段
        skuInfoMapper.updateIsSale(skuId,0);
        searchFeignClient.deleteGoods(skuId);
    }

    // 上架
    @Override
    public void onSale(Long skuId) {
        skuInfoMapper.updateIsSale(skuId,1);

        Goods goods = getGoodsBySkuId(skuId);

        searchFeignClient.saveGoods(goods);

    }

    @Deprecated
    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
        SkuDetailTo detailTo = new SkuDetailTo();
        //0 查询到skuInfo
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

        //1 查询商品所属的完整分类信息
        CategoryViewTo categoryViewTo = baseCategory3Mapper.getCategoryView(skuInfo.getCategory3Id());
        detailTo.setCategoryView(categoryViewTo);

        //2 商品的基本信息
        detailTo.setSkuInfo(skuInfo);

        //3 商品的图片
        List<SkuImage> imageList = skuImageService.getSkuImage(skuId); // 查询到sku的所有图片
        skuInfo.setSkuImageList(imageList);

        //4 实时价格
        BigDecimal price = get1010Price(skuId);
        detailTo.setPrice(price);

        //查出当前sku对应的spu当时定义的销售属性的名和值,并且固定好数据
        List<SpuSaleAttr> saleAttrList = spuSaleAttrService.getSaleAttrAndValueMarkSku(skuInfo.getSpuId(),skuId);
        detailTo.setSpuSaleAttrList(saleAttrList);

        //当前商品（sku）的所有兄弟产品的销售属性名和值组合关系全部查出来，封装成{""188|120:"50"}
        Long id = skuInfo.getId();
        Long spuId = skuInfo.getSpuId();
        String valueJson = spuSaleAttrService.getAllSkuSaleAttrValueJson(spuId);
        detailTo.setValuesSkuJson(valueJson);

        return detailTo;
    }

    @Override
    public BigDecimal get1010Price(Long skuId) {
        BigDecimal price = skuInfoMapper.getRealPrice(skuId);
        return price;
    }

    //查询sku的info信息
    @Override
    public SkuInfo getDetailSkuInfo(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        return skuInfo;
    }
    // 查询sku的图片信息
    @Override
    public List<SkuImage> getDetailSkuImages(Long skuId) {
        List<SkuImage> imageList = skuImageService.getSkuImage(skuId);
        return imageList;
    }

    @Override
    public List<Long> findAllSkuId() {

        return skuInfoMapper.getAllSkuId();
    }
    @Override
    public Goods getGoodsBySkuId(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

        Goods goods = new Goods();
        goods.setId(skuId);
        goods.setDefaultImg(skuInfo.getSkuDefaultImg());
        goods.setTitle(skuInfo.getSkuName());
        goods.setPrice(skuInfo.getPrice().doubleValue());
        goods.setCreateTime(new Date());
        goods.setTmId(skuInfo.getTmId());

        BaseTrademark trademark = baseTrademarkService.getById(skuInfo.getTmId());
        goods.setTmName(trademark.getTmName());
        goods.setTmLogoUrl(trademark.getLogoUrl());

        Long category3Id = skuInfo.getCategory3Id();
        CategoryViewTo view = baseCategory3Mapper.getCategoryView(category3Id);
        goods.setCategory1Id(view.getCategory1Id());
        goods.setCategory1Name(view.getCategory1Name());
        goods.setCategory2Id(view.getCategory2Id());
        goods.setCategory2Name(view.getCategory2Name());
        goods.setCategory3Id(view.getCategory3Id());
        goods.setCategory3Name(view.getCategory3Name());

        goods.setHotScore(0L);
        //TODO 热度分更新

        //查当前sku所有平台属性名和值
        List<SearchAttr> attrs = skuAttrValueService.getSkuAttrNameAndValue(skuId);
        goods.setAttrs(attrs);


        return goods;
    }
}


















