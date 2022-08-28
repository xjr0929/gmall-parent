package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.sun.corba.se.spi.ior.ObjectKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-26-21:52
 */
@Api(tags = "商品详情数据层操作")
@RestController
@RequestMapping("/api/inner/rpc/product")
public class SkuDetailApiController {

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SpuSaleAttrService spuSaleAttrService;

    @Autowired
    BaseCategory3Service baseCategory3Service;

/*    @ApiOperation(value = "查询商品详情")
    @GetMapping("/skudetail/{skuId}")
    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId") Long skuId){
        SkuDetailTo skuDetailTo = skuInfoService.getSkuDetail(skuId);
        return Result.ok(skuDetailTo);
    }*/


    // 查询sku的基本信息
    @GetMapping("/skudetail/info/{skuId}")
    public Result<SkuInfo> getSkuInfo(@PathVariable("skuId") Long skuId){
        SkuInfo skuInfo = skuInfoService.getDetailSkuInfo(skuId);
        return Result.ok(skuInfo);
    }
    // 获取sku的图片信息
    @GetMapping("/skudetail/images/{skuId}")
    public Result<List<SkuImage>> getSkuImages(@PathVariable("skuId") Long skuId){
        List<SkuImage> images = skuInfoService.getDetailSkuImages(skuId);
        return Result.ok(images);
    }
    // 说去实时价格
    @GetMapping("/skudetail/price/{skuId}")
    public Result<BigDecimal> getSku1010Price(@PathVariable("skuId") Long skuId){
        BigDecimal price = skuInfoService.get1010Price(skuId);
        return Result.ok(price);
    }
    // 获取销售属性名和值
    @GetMapping("/skudetail/saleattrvalues/{spuId}/{skuId}")
    public Result<List<SpuSaleAttr>> getSkuSaleattrvalues(@PathVariable("spuId") Long spuId,
                                       @PathVariable("skuId") Long skuId){
        List<SpuSaleAttr> saleAttrList = spuSaleAttrService.getSaleAttrAndValueMarkSku(spuId,skuId);
        return Result.ok(saleAttrList);
    }

    // 查询销售属性组合 valueJson
    @GetMapping("/skudetail/valuejson/{spuId}")
    public Result<String> getSkuValueJson(@PathVariable("spuId") Long spuId){
        String valueJson = spuSaleAttrService.getAllSkuSaleAttrValueJson(spuId);
        return Result.ok(valueJson);
    }

    // 查询分类
    @GetMapping("/skudetail/categoryview/{c3Id}")
    public Result<CategoryViewTo> getCategoryView(@PathVariable("c3Id") Long c3Id){
        CategoryViewTo categoryViewTo = baseCategory3Service.getCategoryView(c3Id);
        return Result.ok(categoryViewTo);
    }


}






















