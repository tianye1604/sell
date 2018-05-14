package com.tianye.sell.service.impl;

import com.tianye.sell.dto.OrderDTO;
import com.tianye.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() {

        OrderDTO orderDTO = orderService.findOne("1525423895489154885");
        pushMessageService.orderStatus(orderDTO);
    }
}