package com.atguigu.gmall.product.mapper;


import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 86136
* @description 针对表【base_category3(三级分类表)】的数据库操作Mapper
* @createDate 2022-08-22 22:33:57
* @Entity com.atguigu.gmall.product.domain.BaseCategory3
*/
public interface BaseCategory3Mapper extends BaseMapper<BaseCategory3> {

    // 根据三级分类id传到对应的二级或一级分类路径
    CategoryViewTo getCategoryView(@Param("category3Id") Long category3Id);
}


























