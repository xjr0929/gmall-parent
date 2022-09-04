package com.atguigu.gmall.search.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Author xjrstart
 * @Date 2022-09-03-21:21
 */
//  replicas 备份
@Data
@Document(indexName = "person",shards = 1,replicas = 1)
public class Person {
    @Id   // 说明这个字段是hi主键
    private Long id;

    @Field(value = "first",type = FieldType.Keyword)
    private String firstName;

    @Field(value = "last",type = FieldType.Keyword)
    private String lastName;

    @Field(value = "age")
    private Integer age;

    @Field(value = "address",type = FieldType.Text,analyzer = "ik_smart")
    private String address;
}

















