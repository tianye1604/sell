package com.tianye.sell.converter;

import com.tianye.sell.dataobject.OrderMaster;
import com.tianye.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
* @Author:tianye
* @Description:
* @Date: 23:52 2018/4/5/005
*/

public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> covert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
