package com.atguigu.gmall.item.cache.impl;

import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.item.cache.CacheOpsService;
import com.atguigu.gmall.common.constant.SysRedisConst;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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

    // 布隆过滤器判断是否有这个商品
    @Override
    public boolean bloomContains(Long skuId) {
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        return filter.contains(skuId);
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

    // 解锁
    @Override
    public void unlock(Long skuId) {
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL + skuId;
        RLock lock = redissonClient.getLock(lockKey);
        // 解锁
        lock.unlock();
    }
}




























