package com.atguigu.gmall.product.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xjrstart
 * @Date 2022-08-24-11:32
 */
@Configuration // 说明这是一个配置类
public class MybatisPlusConfig {

    // 把Mybatis中的插件主体（总插件）放到容器中
    @Bean //相当于把这个组件放到容器中  拦截器
    public MybatisPlusInterceptor interceptor(){
        // 插件主体
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 加入内部的小插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(true); // 页码溢出后默认访问最后一页
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}



















