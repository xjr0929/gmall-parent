package com.atguigu.starter.cache.service;

import java.lang.reflect.Type;

/**
 * @Author xjrstart
 * @Date 2022-08-31-16:29
 */
public interface CacheOpsService {
    // 从缓存中获取一个json并转为普通对象
    <T>T getCacheData(String cacheKey, Class<T> clz);
    // 从缓存中获取一个json并转为复杂对象
    Object getCacheData(String cacheKey, Type type);
    //布隆过滤器判断是否有这个商品
    boolean bloomContains(Object skuId);
    // 从缓存中删一个数据（延迟双删）
    void delay2Delete(String cacheKey);

    boolean tryLock(Long skuId);
    // 把指定对象使用指定的key保存到redis
    void saveData(String cacheKey, Object fromRpc);
    // 以秒为单位
    void saveData(String cacheKey, Object fromRpc, Long dataDdl);

    void unlock(Long skuId);

    // 指定布隆过滤器中是否包含指定值bVal
    boolean bloomContains(String bloomName, Object bVal);
    //加指定的分布式锁
    boolean tryLock(String lockName);
    // 解锁
    void unlock(String lockName);
}





























