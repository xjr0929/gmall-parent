package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.util.DateUtil;
import com.atguigu.gmall.product.config.minio.MinioProperties;
import com.atguigu.gmall.product.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @Author xjrstart
 * @Date 2022-08-25-10:07
 */
@Service
public class FileUploadServiceImpl implements FileUploadService{

    @Autowired
    MinioClient minioClient;

    @Autowired
    MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) throws Exception{
        // 创建出一个MinioClient
        // 判断这个通是否存在
        boolean gmall = minioClient.bucketExists(minioProperties.getBucketName());
        if (!gmall){
            // 桶不存在，顺便创建一个bucket
            minioClient.makeBucket(minioProperties.getBucketName());
        }
        // 说明桶存在  给桶里上传文件
        // objectName： 文件名
        String name = file.getName(); // input的name
        // 唯一文件名
        String dateString = DateUtil.formatDate(new Date());
        String fileName = UUID.randomUUID().toString().replace("-","")+"_"+file.getOriginalFilename(); //真正上传的文件名
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();

        PutObjectOptions options = new PutObjectOptions(file.getSize(),-1l);
        options.setContentType(contentType);
        minioClient.putObject(minioProperties.getBucketName(),
                        dateString +"/"+fileName,
                        inputStream,
                        options);

        String url = minioProperties.getEndpoint()+"/"+minioProperties.getBucketName()+"/"+dateString+"/"+fileName;
        return url;
    }
}






















