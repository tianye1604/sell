package com.tianye.sell.repository;

import com.tianye.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    /** 根据orderId查询订单详情列表 */
    List<OrderDetail> findByOrderId(String orderId);
}
