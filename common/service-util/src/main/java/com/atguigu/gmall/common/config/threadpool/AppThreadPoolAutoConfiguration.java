package com.atguigu.gmall.common.config.threadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author xjrstart
 * @Date 2022-08-28-19:53
 */
//配置线程池
// 开启自动化属性绑定配置
@EnableConfigurationProperties(AppThreadPoolProperties.class)
@Configuration
public class AppThreadPoolAutoConfiguration {

    @Autowired
    AppThreadPoolProperties threadPoolProperties;

    @Value("${spring.application.name}")
    String applicationName;

    @Bean
    public ThreadPoolExecutor coreExecutor(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                threadPoolProperties.getCore(),
                threadPoolProperties.getMax(),
                threadPoolProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(threadPoolProperties.getQueueSize()),
                new ThreadFactory(){ // 负责给线程池创建线程
                    int i = 0;
                    @Override
                    public Thread newThread(Runnable r){
                        Thread thread = new Thread(r);
                        thread.setName(applicationName+"[core-thread-"+i+++"]");
                        return thread;
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return executor;
    }
}




























