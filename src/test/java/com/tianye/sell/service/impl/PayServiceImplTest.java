package com.tianye.sell.service.impl;

import com.tianye.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void create(){
        OrderDTO orderDTO = orderService.findOne("1524237707825263236");
//        OrderDTO orderDTO = new OrderDTO();
        payService.create(orderDTO);
    }

    @Test
    public void refund(){
        OrderDTO orderDTO = orderService.findOne("1525423011052491409");
        payService.refund(orderDTO);
    }

}