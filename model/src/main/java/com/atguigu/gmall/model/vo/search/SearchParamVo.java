package com.atguigu.gmall.model.vo.search;

import lombok.Data;

/**
 * @Author xjrstart
 * @Date 2022-09-05-19:58
 */
// 封装检索条件
/**
 * 检索列表页
 * 检索条件参数：
 * 1）、按照分类； category3Id=61、category2Id、category1Id=2
 * 2）、按照关键字； keyword=小米
 * 3）、按照属性；props=106:安卓手机:手机一级
 *             props=107:1200mAh到3000mAh:电池容量
 * 4）、按照品牌； trademark=1:小米
 * 5）、分页；  pageNo=1
 * 6）、排序；  order=1:desc
 *          1: 代表综合排序【热度分】
 *          2: 代表价格排序
 * vo: view object；专门封装页面数据，给页面交数据
 */
@Data
public class SearchParamVo {
        Long category3Id;
        Long category1Id;
        Long category2Id;
        String keyword;
        String trademark;

        String[] props;

        String order = "1:desc";
        Integer pageNo = 1;
}
