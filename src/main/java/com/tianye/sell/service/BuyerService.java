package com.tianye.sell.service;

import com.tianye.sell.dto.OrderDTO;

/**
 * @Author:tianye
 * @Description:
 * @Date: 11:13 2018/4/6/006
 */

public interface BuyerService {


    /** 查询一个订单 */
    OrderDTO findOrderOne(String openid,String orderId);

    /** 取消一个订单 */
    OrderDTO cancelOrder(String openid,String orderId);

}
