package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author xjrstart
 * @Date 2022-08-22-21:45
 */
@Service //把实现类加入到容器中
public class BaseCategory1ServiceImpl
        extends ServiceImpl<BaseCategory1Mapper,BaseCategory1>
        implements BaseCategory1Service {

}
