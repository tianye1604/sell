package com.tianye.sell.controller;

import com.tianye.sell.dto.OrderDTO;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.exception.SellException;
import com.tianye.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
* @Author:tianye
* @Description: 卖家端订单
* @Date: 14:45 2018/5/8/008
*/

@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param page 第几页, 从1页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
//        orderDTOPage.getTotalPages()
        return new ModelAndView("order/list",map);
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String,Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
            map.put("orderDTO",orderDTO);
        }catch (SellException e) {
            log.error("【卖家订单详情】异常信息，{}",e.getMessage());
            map.put("msg",e.getMessage());
            return new ModelAndView("common/error",map);
        }
        return new ModelAndView("order/detail",map);
    }

    /**
     * 卖家取消订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String,Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch (SellException e) {
            log.error("【卖家取消订单】异常信息，{}",e.getMessage());
            map.put("msg",e.getMessage());
            return new ModelAndView("common/error",map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        return new ModelAndView("common/success",map);
    }

    /**
     * 卖家完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String,Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e) {
            log.error("【卖家完结订单】异常信息，{}",e.getMessage());
            map.put("msg",e.getMessage());
            return new ModelAndView("common/error",map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        return new ModelAndView("common/success",map);
    }

}