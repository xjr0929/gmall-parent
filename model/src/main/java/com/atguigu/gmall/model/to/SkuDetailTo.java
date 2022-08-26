package com.atguigu.gmall.model.to;

import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-26-21:04
 */

@Data
public class SkuDetailTo {
    private CategoryViewTo categoryView; // 当前sku所属的分类信息
    private SkuInfo skuInfo; // 商品的基本信息
    private BigDecimal price; // 实时价格
    private List<SpuSaleAttr> spuSaleAttrList; // spu的所有销售属性列表
    private String valuesSkuJson; //valuesSkuJson
}

























