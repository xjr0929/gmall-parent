package com.atguigu.gmall.product.service;


import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 86136
* @description 针对表【sku_info(库存单元表)】的数据库操作Service
* @createDate 2022-08-23 18:42:09
*/
public interface SkuInfoService extends IService<SkuInfo> {

    void saveSkuInfo(SkuInfo info);
    //下架
    void cancelSale(Long skuId);
    //上架
    void onSale(Long skuId);
    //获取Sku商品数据
    SkuDetailTo getSkuDetail(Long skuId);
    // 查询商品实时价格
    BigDecimal get1010Price(Long skuId);
    //查询sku的基本信息
    SkuInfo getDetailSkuInfo(Long skuId);
    // 查询sku的图片信息
    List<SkuImage> getDetailSkuImages(Long skuId);
    // 查询出所有的skuId
    List<Long> findAllSkuId();

}




















