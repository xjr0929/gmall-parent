package com.atguigu.gmall.item.cache;

import com.atguigu.gmall.model.to.SkuDetailTo;

/**
 * @Author xjrstart
 * @Date 2022-08-31-16:29
 */
public interface CacheOpsService {

    <T>T getCacheData(String cacheKey, Class<T> clz);

    boolean bloomContains(Long skuId);

    boolean tryLock(Long skuId);

    void saveData(String cacheKey, Object fromRpc);

    void unlock(Long skuId);
}





























