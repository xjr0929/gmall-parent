package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.service.SkuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "查询商品详情")
    @GetMapping("/skudetail/{skuId}")
    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId") Long skuId){

        SkuDetailTo skuDetailTo = skuInfoService.getSkuDetail(skuId);

        return Result.ok(skuDetailTo);
    }

}






















