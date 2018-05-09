package com.tianye.sell.service.impl;

import com.tianye.sell.converter.OrderMaster2OrderDTOConverter;
import com.tianye.sell.dataobject.OrderDetail;
import com.tianye.sell.dataobject.OrderMaster;
import com.tianye.sell.dataobject.ProductInfo;
import com.tianye.sell.dto.CartDTO;
import com.tianye.sell.dto.OrderDTO;
import com.tianye.sell.enums.OrderStatusEnum;
import com.tianye.sell.enums.PayStatusEnum;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.exception.SellException;
import com.tianye.sell.repository.OrderDetailRepository;
import com.tianye.sell.repository.OrderMasterRepository;
import com.tianye.sell.service.OrderService;
import com.tianye.sell.service.PayService;
import com.tianye.sell.service.ProductService;
import com.tianye.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
* @Author:tianye
* @Description:
* @Date: 16:08 2018/4/4/004
*/

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();

        String orderId = KeyUtil.getUniqueKey();
        BigDecimal amount = new BigDecimal(0);


        for(OrderDetail orderDetail : orderDetailList){
            //1. 查询商品(数量,价格)
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null) {
                log.error("【创建订单】商品信息不正确，productId:{}",orderDetail.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            BeanUtils.copyProperties(productInfo,orderDetail);

            //2.计算订单总价
            amount = orderDetail.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(amount);

            //orderDetail订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            detailRepository.save(orderDetail);
        }
        //3.写入订单数据库(orderMaster)
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(amount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        masterRepository.save(orderMaster);

        //4.扣库存
        List<CartDTO> cartDTOList = orderDetailList.stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderDTO orderDTO = new OrderDTO();
        OrderMaster orderMaster = masterRepository.findOne(orderId);
        if(orderMaster == null) {
            log.error("【查询订单】订单信息不存在，orderId:{}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = detailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            log.error("【查询订单】订单详情不存在，orderId:{}",orderId);
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findByBuyerOpenid(String openid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = masterRepository.findByBuyerOpenid(openid,pageable);
        List<OrderDTO> orderDTOList  = OrderMaster2OrderDTOConverter.covert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = masterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList  = OrderMaster2OrderDTOConverter.covert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【取消订单】更新失败，orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单详情列表为空，orderDTO = {}",orderDTO);
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(
                e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单完结】订单状态不正确，orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【订单完结】更新失败，orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单支付完成】订单状态不正确，orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】订单支付状态不正确，orderId = {}, payStatus = {}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.PAY_STATAUS_ERROR);
        }
        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【订单支付完成】更新失败，orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
