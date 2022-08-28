package com.atguigu.gmall.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author xjrstart
 * @Date 2022-08-28-20:15
 */

@SpringBootTest
public class ThreadPoolTest {

    @Autowired
    ThreadPoolExecutor executor;
    
    @Test
    public void test(){
        for (int i = 1; i <= 100; i++) {
        executor.submit(() -> {
            System.out.println(Thread.currentThread().getName()+":"+ UUID.randomUUID().toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        }
        try {
            Thread.sleep(10000000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}




















