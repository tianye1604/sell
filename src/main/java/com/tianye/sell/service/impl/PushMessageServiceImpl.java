package com.tianye.sell.service.impl;

import com.tianye.sell.config.WechatAccountConfig;
import com.tianye.sell.dto.OrderDTO;
import com.tianye.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author:tianye
 * @Description: 微信推送模板消息
 * @Date: 16:00 2018/5/14/014
 */

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        templateMessage.setToUser(orderDTO.getBuyerOpenid());

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "您好," + orderDTO.getBuyerName()),
                new WxMpTemplateData("keyword1", orderDTO.getCreateTime().toString()),
                new WxMpTemplateData("keyword2", "￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("keyword3", orderDTO.getOrderDetail()),
                new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5", "40分钟"),
                new WxMpTemplateData("remark", "请你保持电话畅通")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】发送失败,{}", e);
        }
    }
}
