package com.atguigu.gmall.product.init;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.product.service.SkuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author xjrstart
 * @Date 2022-08-31-18:17
 */
// 容器启动成功后连上数据库，查到所有的商品id 在布隆里面进行占位
@Slf4j
@Service
public class SkuIdBloomInitService {

    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    RedissonClient redissonClient;

    @PostConstruct  // 当前组件对象创建成功以后
    public void initSkuBloom(){
        log.info("布隆初始化正在进行....");
        // 查询所有的skuId
        List<Long> skuIds = skuInfoService.findAllSkuId();
        // 把所有的id初始化到布隆过滤器
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        // 初始化布隆过滤器
        boolean exists = filter.isExists();
        if(!exists){
            //尝试初始化。如果布隆过滤器没有初始化过，就尝试初始化
            filter.tryInit(5000000,0.00001);
        }
        //把所有的商品添加到布隆中。 不害怕某个微服务把这个事情做失败
        for (Long skuId : skuIds) {
            filter.add(skuId);
        }


        log.info("布隆初始化完成....，总计添加了 {} 条数据",skuIds.size());
    }

}






























