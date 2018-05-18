package com.tianye.sell.service.impl;

import com.tianye.sell.exception.SellException;
import com.tianye.sell.lock.RedisLock;
import com.tianye.sell.service.SecKillService;
import com.tianye.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:tianye
 * @Description:
 * @Date: 23:35 2018/5/17/017
 */
@Service
public class SecKillServiceImpl implements SecKillService {

    private static final Integer PRODUCT_QUANTITY = 100000;

    private static final Integer TIME_OUT = 10 * 1000;
    /**
     * 国庆活动，皮蛋粥特价，限量100000份
     */
    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;

    static {
        /**
         * 模拟多个表，商品信息表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", PRODUCT_QUANTITY);
        stock.put("123456", PRODUCT_QUANTITY);
    }

    @Autowired
    private RedisLock redisLock;

    private String queryMap(String productId) {
        return "国庆活动，皮蛋粥特价，限量份"
                + products.get(productId)
                + " 还剩: " + stock.get(productId)
                + " 份;"
                + " 该商品成功下单用户数目："
                + orders.size()
                + " 人";
    }


    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public synchronized void orderProductMockDiffUser(String productId) {

        //加锁
        Long currentValue = System.currentTimeMillis() + TIME_OUT;
        if (!redisLock.lock(productId, String.valueOf(currentValue))) {
            throw new SellException(101, "唉吆喂，人太多了，换个姿势再试试~");
        }

        //1.查询该商品库存，为0则活动结束
        Integer stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(100, "活动结束");
        } else {
            //2.下单(模拟不同用户openid不同）
            orders.put(KeyUtil.getUniqueKey(), productId);
            //3.减库存
            stockNum = stockNum - 1;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

        //解锁
        redisLock.unlock(productId, String.valueOf(currentValue));

    }
}
