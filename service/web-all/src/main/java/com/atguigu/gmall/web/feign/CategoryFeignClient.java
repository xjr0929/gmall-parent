package com.atguigu.gmall.web.feign;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-26-20:12
 */
@FeignClient("service-product")  //告诉springboot 这是一个远程调用客户端 ,调用service-product微服务
// 远程调用之前feign会自己找nacos要到service-product 真的地址
@RequestMapping("/api/inner/rpc/product")
public interface CategoryFeignClient {


    //给 service-product微服务发送一个Get方式的请求，路径是/api/inner/rpc/product/category/tree
    // 拿到远程的响应json后转成CategoryTreeTo类型的List集合
    @GetMapping("/category/tree")
    public Result<List<CategoryTreeTo>> getAllCategoryWithTree();

}





















