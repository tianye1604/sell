package com.tianye.sell.VO;

import lombok.Data;

/**
* @Author:tianye
* @Description: 返回结果
* @Date: 15:06 2018/4/3/003
*/
@Data
public class ResultVO<T> {

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
