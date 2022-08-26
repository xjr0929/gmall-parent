package com.atguigu.gmall.model.to;

import lombok.Data;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-26-19:22
 */
//三级分类的属性结构  支持无限层级分类
@Data
public class CategoryTreeTo {
    private Long categoryId;
    private String categoryName;
    private List<CategoryTreeTo> categoryChild; // 子分类
}

























