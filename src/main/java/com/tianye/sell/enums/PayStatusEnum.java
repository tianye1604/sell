package com.tianye.sell.enums;

import lombok.Getter;
/**
* @Author:tianye
* @Description: 支付状态枚举
* @Date: 11:15 2018/4/4/004
*/

@Getter
public enum PayStatusEnum implements CodeEnum{
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
