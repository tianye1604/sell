package com.tianye.sell.service;

import com.tianye.sell.dto.OrderDTO;

public interface PushMessageService {

    void orderStatus(OrderDTO orderDTO);
}
