package com.atguigu.gmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author xjrstart
 * @Date 2022-08-22-21:05
 */
@SpringCloudApplication
@MapperScan("com.atguigu.gmall.product.mapper") //自动扫描所有mapper接口
public class ProductMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductMainApplication.class,args);
    }
}
