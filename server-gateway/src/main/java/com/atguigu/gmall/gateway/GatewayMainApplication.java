package com.atguigu.gmall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author xjrstart
 * @Date 2022-08-22-18:49
 */
//@EnableDiscoveryClient 开启服务发现[1。、导入服务发现jar包， 2、使用这个注解]
//@EnableCircuitBreaker// 开启服务熔断降级 [1、导入jar包sentinel 2、使用这个注解]
//@SpringBootApplication
@SpringCloudApplication  //以上注解的合体
public class GatewayMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMainApplication.class,args);
    }
}























