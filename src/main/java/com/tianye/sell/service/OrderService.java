package com.tianye.sell.service;

import com.tianye.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
* @Author:tianye
* @Description: 订单服务业务
* @Date: 15:32 2018/4/4/004
*/

public interface OrderService {

    /** 创建订单 */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单 */
    OrderDTO findOne(String orderId);

    /** 查询订单列表 */
    Page<OrderDTO> findByBuyerOpenid(String openid,Pageable pageable);

    /** 查询订单列表 */
    Page<OrderDTO> findList(Pageable pageable);

    /** 取消订单 */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单 */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单 */
    OrderDTO paid(OrderDTO orderDTO);


}
