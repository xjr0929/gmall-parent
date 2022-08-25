package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-22-21:17
 */
/*
* 分类请求处理器
* 前后分离：前段发请求，后台处理好后响应JSON数据
* 所有请求全部返回Result对象JSON，所有要写打的数据要放到Result的data属性内即可
* */
    //@ResponseBody 所有的响应数据都是直接写给浏览器（如果是对象就写成json，如果是文本就写成普通字符串）
@Api(tags = "分类请求处理器")
@RequestMapping("/admin/product")
@RestController  //@Controller 这个类是来接受请求的

public class CategoryController {

    //Controller不应该直接操作数据库
    @Autowired
    BaseCategory1Service baseCategory1Service;
    @Autowired
    BaseCategory2Service baseCategory2Service;
    @Autowired
    BaseCategory3Service baseCategory3Service;

    /*
    * 获取所有的一级分类
    * */
    @ApiOperation(value = "获取所有一级分类")
    @GetMapping("/getCategory1")
    public Result getCategory1(){
        //利用mybatis提供好的CRUD方法，查询出所有的一级分类
        List<BaseCategory1> list = baseCategory1Service.list();

        return Result.ok(list);
    }

    //  /admin/product/getCategory2/9
    /*
    * 获取某个一级分类下的所有二级分类
    * */
    @ApiOperation(value = "获取某个一级分类下的所有二级分类")
    @GetMapping("/getCategory2/{c1Id}")
    public Result getCategory2(@PathVariable("c1Id") Long c1Id){
        //查询ciId对应的所有的二级分类
        List<BaseCategory2> category2s = baseCategory2Service.getCategory1Child(c1Id);
        return Result.ok(category2s);
    }

    @ApiOperation(value = "获取某个二级分类下的所有三级分类")
    @GetMapping("/getCategory3/{c2Id}")
    public Result getCategory3(@PathVariable("c2Id") Long c2Id){
        List<BaseCategory3> category3s = baseCategory3Service.getCategory2Child(c2Id);
        return Result.ok(category3s);
    }

}

























