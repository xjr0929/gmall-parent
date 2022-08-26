package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.executor.ResultExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-26-19:16
 * 远程调用都是内部接口，不允许外部访问
 */
@Api(tags = "分类有关的api")
@RequestMapping("/api/inner/rpc/product")
@RestController
public class CategoryApiController {

    @Autowired
    BaseCategory2Service baseCategory2Service;

    @ApiOperation(value = "查询所有分类的树形结构")
    @GetMapping("/category/tree")
    public Result getAllCategoryWithTree(){

       List<CategoryTreeTo> categoryTreeTos = baseCategory2Service.getAllCategoryWithTree();

        return Result.ok(categoryTreeTos);
    }

}

























