package com.tianye.sell.converter;

import com.alibaba.fastjson.JSON;
import com.tianye.sell.dataobject.OrderDetail;
import com.tianye.sell.dto.OrderDTO;
import com.tianye.sell.form.OrderForm;

import java.util.List;

/**
* @Author:tianye
* @Description: 将订单参数信息转换为订单信息
* @Date: 18:48 2018/4/5/005
*/

public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = JSON.parseArray(orderForm.getItems(),OrderDetail.class);

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;

    }
}
