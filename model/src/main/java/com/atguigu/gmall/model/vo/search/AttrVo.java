package com.atguigu.gmall.model.vo.search;

import lombok.Data;

import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-09-05-20:10
 */
// 属性列表中的每个对象
@Data
public class AttrVo {
        private Long attrId;
        private String attrName;
        //每个属性涉及到的所有值集合
        private List<String> attrValueList;
}
