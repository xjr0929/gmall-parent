package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-25-20:30
 */
@Api(tags = "spu功能")
@RestController
@RequestMapping("/admin/product")
public class SpuController {

    @Autowired
    SpuInfoService spuInfoService;
    @Autowired
    SpuImageService spuImageService;

    /*
       @PathVariable: 路径变量
       @RequestParam: 请求参数（请求体中的某个参数）
       @RequestBody： 请求参数（请求体的所有数据）
    */

    @ApiOperation(value = "分页获取Spu")
    @GetMapping("/{pn}/{ps}")
    public Result getSpuPage(@PathVariable("pn") Long pn,
                             @PathVariable("ps") Long ps,
                             @RequestParam Long category3Id){
        Page<SpuInfo> page = new Page<>(pn,ps);
        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id",category3Id);
        Page<SpuInfo> result = spuInfoService.page(page, wrapper);
        return Result.ok(result);
    }
    // spu是用来定义这种商品的所有销售书信（颜色，版本，套餐）
    // sku只是spu当前定义的所有销售属性中的一个精确组合
    @ApiOperation(value = "保存spu信息")
    @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo info){

        //spu大保存  操作4张表
        spuInfoService.saveSpuInfo(info);

        return Result.ok();
    }
    @ApiOperation(value = "查询指定spu的所有图片")
    @GetMapping("/spuImageList/{spuId}")
    public Result spuImageList(@PathVariable("spuId")Long spuId){

        QueryWrapper<SpuImage> wrapper = new QueryWrapper<>();
        wrapper.eq("spu_id",spuId);
        List<SpuImage> list = spuImageService.list(wrapper);
        return Result.ok(list);
    }

}





























