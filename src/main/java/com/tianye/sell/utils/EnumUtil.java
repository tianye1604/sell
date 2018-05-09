package com.tianye.sell.utils;

import com.tianye.sell.enums.CodeEnum;

public class EnumUtil<T> {

    public static <T extends CodeEnum> T getEnumBycode(Integer code, Class<T> className){
        for(T each : className.getEnumConstants()){
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }
}
