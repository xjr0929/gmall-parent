package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-23-18:36
 */
@Api(tags = "操作平台属性")
@RestController  //说明这个类是用来接受请求的
@RequestMapping("/admin/product")
public class BaseAttrController {

    @Autowired
    BaseAttrInfoService baseAttrInfoService;

    @Autowired
    BaseAttrValueService baseAttrValueService;

    ///admin/product/attrInfoList/{category1Id}/{category2Id}/{category3Id}
    //查询某个分类下的所有平台的属性
    @ApiOperation(value = "查询某个分类下的所有平台的属性")
    @GetMapping("/attrInfoList/{c1Id}/{c2Id}/{c3Id}")
    public Result getAttrInfoList(@PathVariable("c1Id") Long c1Id,
                                  @PathVariable("c2Id") Long c2Id,
                                  @PathVariable("c3Id") Long c3Id){

        List<BaseAttrInfo> infos = baseAttrInfoService.getAttrInfoAndValueByCategoryId(c1Id,c2Id,c3Id);

        return Result.ok(infos);
    }

    //保存属性信息
    //前端会把所有页面录入的数据以json的方式post传给我们
    //去除前端发送的请求的请求体中的数据@RequestBody，
    //并把这个JSON数据转成指定的BaseAttrInfo对象，BaseAtrInfo封装前端提交来的所有数据
    @ApiOperation(value = "保存属性信息")
    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo info){

        //平台属性的新增
        baseAttrInfoService.saveAttrInfo(info);
        return Result.ok();
    }

    //根据平台属性id获取完整属性信息
    //getAttrValueList/11
    @ApiOperation(value = "根据平台属性id获取完整属性信息")
    @GetMapping("/getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable("attrId")Long attrId){

        List<BaseAttrValue> values = baseAttrValueService.getAttrValueList(attrId);

        return Result.ok(values);
    }
}























