package com.atguigu.gmall.product.service;


import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86136
* @description 针对表【base_category3(三级分类表)】的数据库操作Service
* @createDate 2022-08-22 22:33:57
*/
public interface BaseCategory3Service extends IService<BaseCategory3> {

    //获取某个二级分类下的所有三级分类
    List<BaseCategory3> getCategory2Child(Long c2Id);
    // 根据3级分类id，查询出整个分类路径
    CategoryViewTo getCategoryView(Long c3Id);
}




















