package com.tianye.sell.utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一键
     * 格式：时间戳+6位随机数
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
