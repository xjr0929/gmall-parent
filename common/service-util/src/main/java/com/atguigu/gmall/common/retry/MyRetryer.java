package com.atguigu.gmall.common.retry;

import feign.RetryableException;
import feign.Retryer;

/**
 * @Author xjrstart
 * @Date 2022-09-02-23:12
 */
// 自定义feign重试次数逻辑
public class MyRetryer implements Retryer {

    private int cur = 0;
    private int max = 0;

    public MyRetryer(){
        cur = 0;
        max = 2;
    }

    //继续重试还是中断重试
    @Override
    public void continueOrPropagate(RetryableException e) {
//        if (cur++ > max){
//            throw e;
//        }
        throw e;
    }

    @Override
    public Retryer clone() {
        return this;
    }
}






















