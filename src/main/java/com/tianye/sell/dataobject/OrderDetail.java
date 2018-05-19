package com.tianye.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* @Author:tianye
* @Description: 订单详情表
* @Date: 10:51 2018/4/4/004
*/

@Entity
@Data
public class OrderDetail implements Serializable {

    @Id
    private String detailId;

    /** 订单主表id */
    private String orderId;

    /** 商品id */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;
    /**
     * 商品数量
     */
    private Integer productQuantity;
    /**
     * 商品小图
     */
    private String productIcon;

}
