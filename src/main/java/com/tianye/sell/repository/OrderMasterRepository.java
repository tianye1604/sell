package com.tianye.sell.repository;

import com.tianye.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{

    /**
     * 根据买家的openid查找订单列表
     * @param openid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String openid, Pageable pageable);
}
