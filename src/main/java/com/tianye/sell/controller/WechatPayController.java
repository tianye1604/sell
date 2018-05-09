package com.tianye.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.tianye.sell.dto.OrderDTO;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.exception.SellException;
import com.tianye.sell.service.OrderService;
import com.tianye.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class WechatPayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        // 1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            log.error("【微信支付】 订单信息不存在，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 2.发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create",map);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){

        PayResponse payResponse = payService.notify(notifyData);

        return new ModelAndView("pay/success");
    }
}
