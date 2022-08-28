package com.atguigu.gmall.common.annotation;


import com.atguigu.gmall.common.config.threadpool.AppThreadPoolAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AppThreadPoolAutoConfiguration.class)
/*
 导入AppThreadPoolAutoConfig 组件
 开启@EnableConfigurationProperties(AppThreadPoolProperties.class)
    -和配置文件绑好
    -AppThreadPoolProperties 放到容器中
 AppThreadPoolAutoConfiguration 给容器中放一个 ThreadPoolExecutor
    随时 @Autowired  ThreadPoolExecutor即可 ，方便修改
*/
public @interface EnableThreadPool {
}
