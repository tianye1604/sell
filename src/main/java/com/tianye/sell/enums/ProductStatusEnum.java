package com.tianye.sell.enums;

import lombok.Getter;

/**
* @Author:tianye
* @Description:
* @Date: 10:51 2018/4/3/003
*/
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0,"在架"),
    DOWN(1,"下架")
    ;
    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
