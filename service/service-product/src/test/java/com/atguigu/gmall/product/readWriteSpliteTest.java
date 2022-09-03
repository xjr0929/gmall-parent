package com.atguigu.gmall.product;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.mapper.BaseTrademarkMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author xjrstart
 * @Date 2022-09-02-20:47
 */
@SpringBootTest
public class readWriteSpliteTest {

    @Autowired
    BaseTrademarkMapper baseTrademarkMapper;

    @Test
    public void testrw(){
        BaseTrademark baseTrademark = baseTrademarkMapper.selectById(4l);
        System.out.println("baseTrademark = " + baseTrademark);
    }
    
}


























