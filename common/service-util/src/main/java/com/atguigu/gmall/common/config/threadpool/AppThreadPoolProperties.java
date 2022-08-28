package com.atguigu.gmall.common.config.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xjrstart
 * @Date 2022-08-28-20:05
 */
@Data
@ConfigurationProperties(prefix = "app.thread-pool")
public class AppThreadPoolProperties {

    Integer core = 2;
    Integer max = 4;
    Integer queueSize = 200;
    Long keepAliveTime = 300l;

}





















