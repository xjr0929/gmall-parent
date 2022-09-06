package com.atguigu.gmall.item.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.search.SearchFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.to.SkuDetailTo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author xjrstart
 * @Date 2022-08-26-21:33
 */
@Api(tags = "三级分类的额RPC接口")
@RestController
@RequestMapping("/api/inner/rpc/item")
public class SkuDetailApiController {

    @Autowired
    SkuDetailService skuDetailService;

    @Autowired
    SearchFeignClient searchFeignClient;

    @GetMapping("/skudetail/{skuId}")
    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId")Long skuId) {
        //商品详情
        SkuDetailTo skuDetailTo = skuDetailService.getSkuDetail(skuId);
        // 更新热度分 分批更新
        skuDetailService.updateHotScore(skuId);

        return Result.ok(skuDetailTo);
    }
}























