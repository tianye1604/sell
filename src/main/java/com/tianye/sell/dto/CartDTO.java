package com.tianye.sell.dto;

import lombok.Data;

/**
* @Author:tianye
* @Description: 购物车信息
* @Date: 16:20 2018/4/4/004
*/
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
