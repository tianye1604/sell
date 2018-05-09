package com.tianye.sell.exception;

import com.tianye.sell.enums.ResultEnum;
/**
* @Author:tianye
* @Description:
* @Date: 16:40 2018/4/4/004
*/

public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }


    public SellException(Integer code) {
        this.code = code;
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
