package com.atguigu.gmall.product;

import com.atguigu.gmall.common.annotation.EnableThreadPool;
import com.atguigu.gmall.common.config.RedissonAutoConfiguration;
import com.atguigu.gmall.common.config.Swagger2Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author xjrstart
 * @Date 2022-08-22-21:05
 */
//批量导入：@ComponentScan("com.atguigu.gmall.profuct.mapper") // 自动扫描这个包下的所有Mapper接口
// @SpringBootApplication(scanBasePackages = "com.atguigu.gmall")
//精准导入: @Import({Swagger2Config.class})
@EnableFeignClients(basePackages = {"com.atguigu.gmall.feign.search"})
@EnableScheduling
@EnableThreadPool
@Import({Swagger2Config.class})
@SpringCloudApplication
@MapperScan("com.atguigu.gmall.product.mapper") //自动扫描所有mapper接口
public class ProductMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductMainApplication.class,args);
    }
}
