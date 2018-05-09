package com.tianye.sell.controller;

import com.tianye.sell.VO.ResultVO;
import com.tianye.sell.converter.OrderForm2OrderDTOConverter;
import com.tianye.sell.dto.OrderDTO;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.exception.SellException;
import com.tianye.sell.form.OrderForm;
import com.tianye.sell.service.BuyerService;
import com.tianye.sell.service.OrderService;
import com.tianye.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:tianye
 * @Description:
 * @Date: 18:07 2018/4/5/005
 */

@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        //校验参数
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数信息不正确，orderForm:{}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //将orderForm数据转换为orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        //检验购物车参数
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车信息不正确，orderForm:{}",orderForm);
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        //创建订单
        OrderDTO createResult = orderService.create(orderDTO);

        Map<String,String> result = new HashMap<>();
        result.put("orderId",createResult.getOrderId());
        //构建返回数据
        return ResultVOUtil.success(result);
    }


    //订单列表
    @GetMapping("/list")
    public ResultVO<OrderDTO> list(@RequestParam("openid") String openid,
                                   @RequestParam(value="page",defaultValue = "0") Integer page,
                                   @RequestParam(value="size",defaultValue = "10") Integer size) {
        if(StringUtils.isEmpty(openid)){
            log.error("【订单列表】买家openid为空，openid:{}",openid);
            throw new SellException(ResultEnum.OPENID_EMPTY);
        }

        PageRequest pageRequest = new PageRequest(page,size);

        Page<OrderDTO> orderDTOPage = orderService.findByBuyerOpenid(openid,pageRequest);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }


    //查询订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        if(StringUtils.isEmpty(openid)||StringUtils.isEmpty(orderId)){
            log.error("【查询订单详情】查询参数不正确");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO  orderDTO = buyerService.findOrderOne(openid,orderId);

        return ResultVOUtil.success(orderDTO);
    }


    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
