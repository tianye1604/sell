package com.tianye.sell.VO;

import lombok.Data;

import java.io.Serializable;

/**
* @Author:tianye
* @Description: 返回结果
* @Date: 15:06 2018/4/3/003
*/
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = -4074708848156578284L;

    private Integer code;

    private String msg;

    private T data;

    public ResultVO() {
    }

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
