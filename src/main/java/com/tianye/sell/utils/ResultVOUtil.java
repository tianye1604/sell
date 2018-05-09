package com.tianye.sell.utils;

import com.tianye.sell.VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object data){
        return new ResultVO(0,"成功",data);
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        return new ResultVO(code,msg);
    }
}
