package com.tianye.sell.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import com.tianye.sell.dto.OrderDTO;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.exception.SellException;
import com.tianye.sell.service.OrderService;
import com.tianye.sell.service.PayService;
import com.tianye.sell.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信支付测试";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest request = new PayRequest();

        //支付请求参数
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        request.setOrderId(orderDTO.getOrderId());
        request.setOpenid(orderDTO.getBuyerOpenid());
        request.setOrderName(ORDER_NAME);
        request.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        log.info("【发起支付】 request={}", JsonUtil.toJson(request));

        PayResponse payResponse = bestPayService.pay(request);
        log.info("【发起支付】 payResponse={}",JsonUtil.toJson(payResponse));

        return payResponse;
    }

    @Override
    public PayResponse notify(String nofityData) {

        // 1、检查签名
        PayResponse payResponse = bestPayService.asyncNotify(nofityData);
        log.info("【微信异步消息】, payResponse={}", JsonUtil.toJson(payResponse));

        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        // 2.检查订单信息是否为空
        if(orderDTO == null){
            log.error("【微信异步消息】 订单信息为空，ordereId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 3、检查金额是否匹配
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信异步消息】 消息金额与系统金额不匹配,\n" +
                    "消息金额为：{}\n"+
                    "系统金额为：{}\n"+
                    "orderId={}",payResponse.getOrderAmount(),orderDTO.getOrderAmount(),orderDTO.getOrderId());
            throw new SellException(ResultEnum.WXPAY_MONEY_VALIDATE_ERROR);
        }

        orderService.paid(orderDTO);

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request = {}", JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response = {}",JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}
