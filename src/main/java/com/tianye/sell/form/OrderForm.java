package com.tianye.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
* @Author:tianye
* @Description: 订单信息表单验证
* @Date: 18:25 2018/4/5/005
*/
@Data
public class OrderForm {

    /** 买家名称 */
    @NotBlank(message = "买家名称必填")
    private String name;

    /** 买家电话 */
    @NotBlank(message = "买家电话必填")
    private String phone;

    /** 买家地址 */
    @NotBlank(message = "买家地址必填")
    private String address;

    /** 买家微信openid */
    @NotBlank(message = "买家微信openid必填")
    private String openid;

    /** 购物车信息 */
    @NotBlank(message = "购物车信息不能为空")
    private String items;
}
