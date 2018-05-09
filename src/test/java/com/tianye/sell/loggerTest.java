package com.tianye.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
* @Author:tianye
* @Description: 日志测试
* @Date: 21:22 2018/4/4/004
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class loggerTest {

    @Test
    public void testLogger(){
        log.debug("debug……");
        log.info("info……");
        log.warn("warn……");
        log.error("error……");


    }

}
