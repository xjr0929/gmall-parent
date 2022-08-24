package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86136
* @description 针对表【base_attr_info(属性表)】的数据库操作Service实现
* @createDate 2022-08-23 18:42:09
*/
@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo>
    implements BaseAttrInfoService {

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    @Override
    public List<BaseAttrInfo> getAttrInfoAndValueByCategoryId(Long c1Id, Long c2Id, Long c3Id) {

        //sql查询指定分类下的所有属性名和值
        List<BaseAttrInfo> infos = baseAttrInfoMapper.getAttrInfoAndValueByCategoryId(c1Id,c2Id,c3Id);

        return infos;
    }

    //保存平台属性
    @Override
    public void saveAttrInfo(BaseAttrInfo info) {

        //判断什么情况是新增  什么情况是修改
        if (info.getId() == null){
            //没有id  新增
            addBaseAttrInfo(info);
        }else {
            //有id  修改
            updateBaseAttrInfo(info);
        }

    }

    private void updateBaseAttrInfo(BaseAttrInfo info) {
        // 该属性名信息
        baseAttrInfoMapper.updateById(info);
        // 该属性值
        List<BaseAttrValue> valueList = info.getAttrValueList();

        //删除
        //前端提交来的所有属性值id
        List<Long> vids = new ArrayList<>();
        for (BaseAttrValue attrValue : valueList) {
            Long id = attrValue.getId();
            if (id != null){
                vids.add(id);
            }
        }
        if (vids.size() > 0) {
            //部分删除
            QueryWrapper<BaseAttrValue> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("attr_id", info.getId());
            deleteWrapper.notIn("id", vids);
            baseAttrValueMapper.delete(deleteWrapper);
        }else {
            //全部删除,前端一个id都没有带，把id下的所有属性值全部删除
            QueryWrapper<BaseAttrValue> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("attr_id", info.getId());
            baseAttrValueMapper.delete(deleteWrapper);
        }
        for (BaseAttrValue attrValue : valueList) {

            if (attrValue.getId() != null){
                //说明有id，执行修改操作
                baseAttrValueMapper.updateById(attrValue);
            }
            if (attrValue.getId() == null) {
                //说明没有id ，执行新增操作
                attrValue.setAttrId(info.getId());
                baseAttrValueMapper.insert(attrValue);
            }
        }
    }

    private void addBaseAttrInfo(BaseAttrInfo info) {
        //新增 保存属性名
        baseAttrInfoMapper.insert(info);
        //拿到方才保存好的属性名的自增id
        Long id = info.getId();

        //保存属性值
        List<BaseAttrValue> valueList = info.getAttrValueList();
        for (BaseAttrValue value : valueList) {
            //回填属性名记录的自增id
            value.setAttrId(id);
            baseAttrValueMapper.insert(value);
        }
    }


}































