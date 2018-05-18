package com.tianye.sell.controller;

import com.tianye.sell.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:tianye
 * @Description: 秒杀活动
 * @Date: 23:52 2018/5/17/017
 */
@RestController
@RequestMapping("/skill")
@Slf4j
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    /**
     * 查询秒杀活动特价商品的信息
     *
     * @param productId
     * @return
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) {
        return secKillService.querySecKillProductInfo(productId);
    }


    /**
     * 秒杀，没有抢到获得"唉吆喂，xxxxx",抢到了返回剩余的库存量
     *
     * @param productId
     * @return
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) {
        log.info("@skill request,productId:{}", productId);
        secKillService.orderProductMockDiffUser(productId);
        return secKillService.querySecKillProductInfo(productId);

    }
}
