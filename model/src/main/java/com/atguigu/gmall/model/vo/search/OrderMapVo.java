package com.atguigu.gmall.model.vo.search;

import lombok.Data;

/**
 * @Author xjrstart
 * @Date 2022-09-05-20:10
 */
// 升降序规则
@Data
public class OrderMapVo {
    private String type; //排序类型， 1是综合，2是价格
    private String sort; //排序规则
}
