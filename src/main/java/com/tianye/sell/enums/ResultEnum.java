package com.tianye.sell.enums;


import lombok.Getter;

/**
* @Author:tianye
* @Description:
* @Date: 16:35 2018/4/4/004
*/
@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),
    ORDER_CANCEL_SUCCESS(1,"订单取消成功"),
    ORDER_FINISH_SUCCESS(2,"订单完结成功"),
    PRODUCT_UP_SUCCESS(3, "商品上架成功"),
    PRODUCT_DOWN_SUCCESS(4, "商品下架成功"),

    PRODUCT_NOT_EXIST(100,"商品信息不存在"),
    PRODUCT_STOCK_ERROR(101,"商品库存不正确"),
    ORDER_NOT_EXIST(102,"订单信息不存在"),
    ORDERDETAIL_NOT_EXIST(103,"订单详细信息不存在"),
    PARAM_ERROR(104,"参数不正正确"),
    CART_EMPTY(105,"购物车信息不正确"),
    OPENID_EMPTY(106,"买家openid为空"),
    ORDER_STATUS_ERROR(107,"订单状态不正确"),
    PAY_STATAUS_ERROR(108,"支付状态不正确"),
    ORDER_UPDATE_FAIL(109,"订单更新失败"),
    ORDER_DETAIL_EMPTY(110,"订单详情列表为空"),
    ORDER_OWNER_ERROR(111,"该订单openid不存在"),
    WECHAT_AUTH_ERROR(112,"微信网页授权错误"),
    WXPAY_MONEY_VALIDATE_ERROR(113,"异步信息金额与系统金额不匹配"),
    PRODUCT_STATUS_ERROR(114, "商品状态不正确"),
    PRODUCT_UP_ERROR(115, "商品上架失败"),
    PRODUCT_DOWN_ERROR(116, "商品下架失败"),

    ;

    private Integer code;

    private String  message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
