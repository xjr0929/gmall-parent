package com.atguigu.starter.cache.service.impl;

import com.atguigu.starter.cache.constant.SysRedisConst;
import com.atguigu.starter.cache.service.CacheOpsService;
import com.atguigu.starter.cache.utils.Jsons;
import com.fasterxml.jackson.core.type.TypeReference;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @Author xjrstart
 * @Date 2022-08-31-16:29
 */
//封装了缓存操作
@Service
public class CacheOpsServiceImpl implements CacheOpsService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RedissonClient redissonClient;
    // 专门指定延迟任务的线程池
    ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(4);

    //从缓存中获取一个数据，并转换成指定类型的对象
    @Override
    public <T> T getCacheData(String cacheKey, Class<T> clz) {
        String jsonStr = redisTemplate.opsForValue().get(cacheKey);
        if (SysRedisConst.NULL_VAL.equals(jsonStr)){
            return null;
        }
        T t = Jsons.toObj(jsonStr,clz);
        return t;
    }

    @Override
    public Object getCacheData(String cacheKey, Type type) {
        String jsonStr = redisTemplate.opsForValue().get(cacheKey);
        if (SysRedisConst.NULL_VAL.equals(jsonStr)){
            return null;
        }
        // 逆转json为Type类型的复杂对象
        Object obj = Jsons.toObj(jsonStr, new TypeReference<Object>() {
            @Override
            public Type getType() {
                return type; // 这个是方法带泛型的返回值类型
            }
        });
        return obj;
    }

    // 布隆过滤器判断是否有这个商品
    @Override
    public boolean bloomContains(Object skuId) {
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        return filter.contains(skuId);
    }

    // 延迟双删
    @Override
    public void delay2Delete(String cacheKey) {
        redisTemplate.delete(cacheKey);
        // 提交一个延时任务
        // 断点失效：使用分布式池框架  Redisson
        scheduledExecutor.schedule(() -> {
            redisTemplate.delete(cacheKey);
        },5,TimeUnit.SECONDS);
    }

    // 给指定商品加上锁
    @Override
    public boolean tryLock(Long skuId) {
        //准备一个锁用的key
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL + skuId;
        RLock lock = redissonClient.getLock(lockKey);
        //尝试加锁
        boolean tryLock = lock.tryLock();
        return tryLock;
    }

    // 把指定对象使用指定的key保存到redis
    @Override
    public void saveData(String cacheKey, Object fromRpc) {
        if (fromRpc == null){
            //null 值缓存久一点时间
        redisTemplate.opsForValue().set(cacheKey,SysRedisConst.NULL_VAL,SysRedisConst.NULL_VAL_TTL, TimeUnit.SECONDS);
        }else {
            String str = Jsons.toStr(fromRpc);
            redisTemplate.opsForValue().set(cacheKey,str,SysRedisConst.SKUDETAIL_TTL,TimeUnit.SECONDS);
        }
    }
    // 把指定对象使用指定的key保存到redis,以秒为单位
    @Override
    public void saveData(String cacheKey, Object fromRpc, Long dataDdl) {
        if (fromRpc == null){
            //null 值缓存久一点时间
            redisTemplate.opsForValue().set(cacheKey,SysRedisConst.NULL_VAL,SysRedisConst.NULL_VAL_TTL, TimeUnit.SECONDS);
        }else {
            String str = Jsons.toStr(fromRpc);
            redisTemplate.opsForValue().set(cacheKey,str,SysRedisConst.SKUDETAIL_TTL,TimeUnit.SECONDS);
        }
    }

    // 解锁
    @Override
    public void unlock(Long skuId) {
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL + skuId;
        RLock lock = redissonClient.getLock(lockKey);
        // 解锁
        lock.unlock();
    }

    @Override
    public boolean bloomContains(String bloomName, Object bVal) {
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(bloomName);// 拿到指定名字的布隆过滤器
        return filter.contains(bVal);
    }

    @Override
    public boolean tryLock(String lockName) {
        RLock rLock = redissonClient.getLock(lockName);
        return rLock.tryLock();
    }

    @Override
    public void unlock(String lockName) {
        RLock lock = redissonClient.getLock(lockName);
        lock.unlock(); //防止删别人的锁
    }
}




























