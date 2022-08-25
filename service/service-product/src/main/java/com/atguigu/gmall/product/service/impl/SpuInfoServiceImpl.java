package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.product.SpuSaleAttrValue;
import com.atguigu.gmall.product.mapper.SpuImageMapper;
import com.atguigu.gmall.product.mapper.SpuInfoMapper;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.product.service.SpuSaleAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 86136
* @description 针对表【spu_info(商品表)】的数据库操作Service实现
* @createDate 2022-08-23 18:42:09
*/
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo>
    implements SpuInfoService {

    @Autowired
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SpuImageService spuImageService;
    @Autowired
    SpuSaleAttrService spuSaleAttrService;
    @Autowired
    SpuSaleAttrValueService spuSaleAttrValueService;

    @Transactional
    @Override
    public void saveSpuInfo(SpuInfo info) {
        // 先把spu的基本信息保存到spu_info表中  Mybatis自己会把数据存在表里
        spuInfoMapper.insert(info);
        // 保存后的自增id，回填
        Long spuId = info.getId();
        // 把spu的图片保存到spu_image表中
        List<SpuImage> imageList = info.getSpuImageList();
        for (SpuImage image : imageList) {
            //回填
            image.setSpuId(spuId);
        }
        // 批量保存图片
        spuImageService.saveBatch(imageList);

        // 保存销售属性名 到 spu_sale_attr表中
        List<SpuSaleAttr> attrNameList = info.getSpuSaleAttrList();
        for (SpuSaleAttr attr : attrNameList) {
            // 回填spuId
            attr.setSpuId(spuId);
            // 属性名对应的所有销售属性值集合
            List<SpuSaleAttrValue> valueList = attr.getSpuSaleAttrValueList();
            for (SpuSaleAttrValue value : valueList) {
                value.setSpuId(spuId);// 回填spu_id
                String saleAttrName = attr.getSaleAttrName(); // 获取销售属性名
                value.setSaleAttrName(saleAttrName);// 回填销售属性名
            }
            // 批量保存销售属性值到数据库
            spuSaleAttrValueService.saveBatch(valueList);
        }
        // 批量保存到数据库
        spuSaleAttrService.saveBatch(attrNameList);
    }
        // 销售属性值
}




















