package com.tianye.sell.repository;

import com.tianye.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    /**
     * 根据openId查询卖家信息
     *
     * @param openid
     * @return
     */
    SellerInfo findByOpenid(String openid);
}
