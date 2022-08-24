package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86136
* @description 针对表【base_attr_value(属性值表)】的数据库操作Service实现
* @createDate 2022-08-23 18:42:09
*/
@Service
public class BaseAttrValueServiceImpl extends ServiceImpl<BaseAttrValueMapper, BaseAttrValue>
    implements BaseAttrValueService {

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    //根据平台属性id查询出这个属性的所有属性值
    @Override
    public List<BaseAttrValue> getAttrValueList(Long attrId) {

        QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id",attrId);
        List<BaseAttrValue> values = baseAttrValueMapper.selectList(wrapper);
        return values;
    }
}


























