package com.atguigu.gmall.product.config.minio;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xjrstart
 * @Date 2022-08-25-19:36
 */
@Data
@Component // 容器中的组件
//自动把配置文件中app.minio写的配置的每个属性全部和这个javaBean的实行一一对应
@ConfigurationProperties(prefix = "app.minio")
public class MinioProperties {

    String endpoint;  // 读取到配置文件的值

    String ak;

    String sk;

    String bucketName;
}




















