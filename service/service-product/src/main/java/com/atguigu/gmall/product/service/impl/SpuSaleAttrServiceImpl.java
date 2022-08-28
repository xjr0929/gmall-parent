package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.ValueSkuJsonTo;
import com.atguigu.gmall.product.mapper.SpuSaleAttrMapper;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 86136
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Service实现
* @createDate 2022-08-23 18:42:09
*/
@Service
public class SpuSaleAttrServiceImpl extends ServiceImpl<SpuSaleAttrMapper, SpuSaleAttr>
    implements SpuSaleAttrService {

    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;

    @Override
    public List<SpuSaleAttr> getSaleAttrAndValueBySpuId(Long spuId) {
        List<SpuSaleAttr> list = spuSaleAttrMapper.getSaleAttrAndValueBySpuId(spuId);
        return list;
    }

    @Override
    public List<SpuSaleAttr> getSaleAttrAndValueMarkSku(Long spuId, Long skuId) {

        return spuSaleAttrMapper.getSaleAttrAndValueMarkSku(spuId,skuId);
    }

    @Override
    public String getAllSkuSaleAttrValueJson(Long spuId) {
        List<ValueSkuJsonTo> valueSkuJsonTos = spuSaleAttrMapper.getAllSkuValueJson(spuId);

        Map<String,Long> map = new HashMap<>();
        for (ValueSkuJsonTo valueSkuJsonTo : valueSkuJsonTos) {
            String valueJson = valueSkuJsonTo.getValueJson();  // 118|120
            Long skuId = valueSkuJsonTo.getSkuId();  // 49
            map.put(valueJson,skuId);
        }
        //把map转换为json  springboot自带的jackson
        String json = Jsons.toStr(map);
        return json;
    }
}
























