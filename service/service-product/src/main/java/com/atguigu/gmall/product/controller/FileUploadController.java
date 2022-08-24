package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xjrstart
 * @Date 2022-08-24-16:52
 */
@RestController
@RequestMapping("/admin/product")
public class FileUploadController {
    /*
    *  文件上传
    * 前端把文件路放到哪里？
    *   Post请求数据放在请求体（包含了文件流）
    * 如何接：
    *  @RequestParam("file")MultipartFile file
    * @RequestPart("file")MultipartFile file :专门处理文件的
    *
    *  各种注解接不同位置的请求数据
    * @RequestParam: 无论是什么请求  接请求参数 用一个Pojo把所有数据都接了
    * @RequestPart: 接请求参数里面的文件项
    * @RequestBody: 接请求体中的所有数据  （json转为pojo）
    * @RequestVariable: 就路径上的动态变量
    * @RequestHeader: 获取浏览器发送的请求的请求头中的某些值
    * @CookieValue: 获取浏览器发送的请求的cookie值
    * - 如果多个就写数组 ，否则就写单个对象
    * */
    @PostMapping("/fileUpload")
    public Result fileUpload(){

        return Result.ok();
    }
}





























