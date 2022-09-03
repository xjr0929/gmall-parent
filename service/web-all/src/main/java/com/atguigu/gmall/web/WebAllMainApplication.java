package com.atguigu.gmall.web;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author xjrstart
 * @Date 2022-08-26-9:27
 * 前端项目-页面跳转与数据渲染
 */
@EnableFeignClients(basePackages = {"com.atguigu.gmall.feign.item","com.atguigu.gmall.feign.product"})  // 开启远程调用功能  只会扫描主程序所在的子包
@SpringCloudApplication
public class WebAllMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAllMainApplication.class,args);
    }
}
