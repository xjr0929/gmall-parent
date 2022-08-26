package com.atguigu.gmall.product.service;


import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86136
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Service
* @createDate 2022-08-23 18:42:09
*/
public interface SpuSaleAttrService extends IService<SpuSaleAttr> {

    // 根据spuId查询对应的所有销售属性的名和值
    List<SpuSaleAttr> getSaleAttrAndValueBySpuId(Long spuId);
    //查询当前sku对应的spu定义的所有销售属性名和值并且标记好当前sku属于那一种组合
    List<SpuSaleAttr> getSaleAttrAndValueMarkSku(Long spuId, Long skuId);
}
