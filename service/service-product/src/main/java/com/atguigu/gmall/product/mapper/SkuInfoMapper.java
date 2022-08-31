package com.atguigu.gmall.product.mapper;


import com.atguigu.gmall.model.product.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 86136
 * @description 针对表【sku_info(库存单元表)】的数据库操作Mapper
 * @createDate 2022-08-23 18:42:09
 * @Entity com.atguigu.gmall.product.domain.SkuInfo
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    void updateIsSale(@Param("skuId") Long skuId, @Param("sale") int sale);

    //查询一个商品的实时价格
    BigDecimal getRealPrice(@Param("skuId") Long skuId);

    //查询所有的skuId
    List<Long> getAllSkuId();
}




















