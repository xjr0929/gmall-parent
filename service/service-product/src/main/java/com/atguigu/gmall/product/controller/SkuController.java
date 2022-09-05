package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-25-22:14
 */
@Api(tags = "sku功能")
@RestController
@RequestMapping("/admin/product")
public class SkuController {

    @Autowired
    SkuInfoService skuInfoService;


    @ApiOperation(value = "分页查询sku信息")
    @GetMapping("list/{pn}/{ps}")
    public Result getSkuList(@PathVariable("pn")Long pn,
                             @PathVariable("ps")Long ps){

        Page<SkuInfo> page = new Page<>(pn,ps);
        Page<SkuInfo> result = skuInfoService.page(page);

        return Result.ok(result);
    }

    @ApiOperation(value = "sko大保存")
    @PostMapping("/saveSkuInfo")
    public Result saveSku(@RequestBody SkuInfo info){

        skuInfoService.saveSkuInfo(info);
        return Result.ok();
    }
    @ApiOperation(value = "下架")
    @GetMapping("/cancelSale/{skuId}")
    public Result cancelSale(@PathVariable("skuId")Long skuId){
         skuInfoService.cancelSale(skuId);
         return Result.ok();
    }
    @ApiOperation(value = "上架")
    @GetMapping("onSale/{skuId}")
    public Result onSale(@PathVariable("skuId")Long skuId){
        skuInfoService.onSale(skuId);
        return Result.ok();
    }

}

















