package com.tianye.sell.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:tianye
 * @Description: 商品提交表单
 * @Date: 16:05 2018/5/9/009
 */

@Data
public class ProductForm {

    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品图片
     */
    private String productIcon;

    /**
     * 商品类目编号
     */
    private Integer categoryType;
}
