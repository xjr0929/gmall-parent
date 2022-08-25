package com.atguigu.gmall.product;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;

/**
 * @Author xjrstart
 * @Date 2022-08-25-9:21
 */
/*
* Test要用junit5
* */
@SpringBootTest  // 可以测试SpringBoot的所有组件
public class MinioTest {

    @Test
    public void uploadFile() throws Exception{
        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient("http://192.168.6.200:9000",
                    "admin",
                    "admin123456");

            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists("gmall");
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket("gmall");
            }

            // 使用putObject上传一个文件到存储桶中。
            FileInputStream inputStream = new FileInputStream("E:\\220310JavaEE(西安)\\尚品汇\\尚品汇\\资料\\03 商品图片\\品牌\\oppo.png");
            PutObjectOptions options = new PutObjectOptions(inputStream.available(),-1l);
            minioClient.putObject("gmall","oppo.png",
                    inputStream,
                    options);
            System.out.println("上传成功");
        } catch(MinioException e) {
            System.out.println("上传失败" + e);
        }
    }
}
