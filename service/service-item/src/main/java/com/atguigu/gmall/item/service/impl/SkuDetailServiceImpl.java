package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.feign.SkuDetailFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author xjrstart
 * @Date 2022-08-26-21:44
 */
@Service
public class SkuDetailServiceImpl implements SkuDetailService {

    @Autowired
    SkuDetailFeignClient skuDetailFeignClient;

    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
        SkuDetailTo detailTo = new SkuDetailTo();

        Result<SkuDetailTo> skuDetail = skuDetailFeignClient.getSkuDetail(skuId);

        return skuDetail.getData();
    }
}






























