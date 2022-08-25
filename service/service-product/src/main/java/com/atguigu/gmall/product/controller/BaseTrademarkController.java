package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-24-11:19
 */
@Api(tags = "品牌操作")
@RestController
@RequestMapping("/admin/product")
public class BaseTrademarkController {

    @Autowired
    BaseTrademarkService baseTrademarkService;

    //分页查询所有品牌
    @ApiOperation(value = "分页查询所有品牌")
    @GetMapping("/baseTrademark/{pageNum}/{pageSize}")
    public Result baseTrademark(@PathVariable("pageNum") Long pageNum,
                                @PathVariable("pageSize") Long pageSize){
        //long current，long size
        Page<BaseTrademark> page = new Page<>(pageNum,pageSize);

        //分页查询 (pageResult中既有分页信息，又有查询到的记录集合)
        Page<BaseTrademark> pageResult = baseTrademarkService.page(page);

//        pageResult.getTotal();  获取总页数
//        pageResult.getCurrent();  当前是第几页

        return Result.ok(pageResult);
    }

    // 根据品牌id获取品牌信息
    @ApiOperation(value = "根据品牌id获取品牌信息")
    @GetMapping("/baseTrademark/get/{id}")
    public Result getBaseTrademark(@PathVariable("id") Long id){
        BaseTrademark trademark = baseTrademarkService.getById(id);
        return Result.ok(trademark);
    }

        // 修改平拍信息
    @ApiOperation(value = "修改平拍信息")
    @PutMapping("/baseTrademark/update")
    //数据是json，BaseTrademark：让json自动转为品牌
    public Result updateBaseTrademark(@RequestBody BaseTrademark trademark){
        //根据id修改品牌信息
        baseTrademarkService.updateById(trademark);
        return Result.ok();
    }
    // 保存品牌
    @ApiOperation(value = "保存品牌")
    @PostMapping("/baseTrademark/save")
    public Result saveBaseTrademark(@RequestBody BaseTrademark trademark){
        baseTrademarkService.save(trademark);
        return Result.ok();
    }
    // 根据id删除品牌  /baseTrademark/remove/2
    @ApiOperation(value = "根据id删除品牌")
    @DeleteMapping("/baseTrademark/remove/{tid}")
    public Result deleteBaseTrademark(@PathVariable("tid")Long tid){
        baseTrademarkService.removeById(tid);
        return Result.ok();
    }

    @ApiOperation(value = "查询所有品牌")
    @GetMapping("/baseTrademark/getTrademarkList")
    public Result getTrademarkList(){

        List<BaseTrademark> list = baseTrademarkService.list();

        return Result.ok(list);
    }
}



























