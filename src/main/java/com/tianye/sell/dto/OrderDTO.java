package com.tianye.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tianye.sell.dataobject.OrderDetail;
import com.tianye.sell.enums.OrderStatusEnum;
import com.tianye.sell.enums.PayStatusEnum;
import com.tianye.sell.utils.EnumUtil;
import com.tianye.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @Author:tianye
* @Description:
* @Date: 15:59 2018/4/4/004
*/

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    
    
    private String orderId;

    /** 买家名称 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态，默认为0是新下单 */
    private Integer orderStatus;

    /** 支付状态，默认为0是未支付 */
    private Integer payStatus;

    /** 创建时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 修改时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 商品详情列表 */
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getEnumBycode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getEnumBycode(payStatus,PayStatusEnum.class);

    }

    @JsonIgnore
    public String getOrderDetail() {
        StringBuffer orders = new StringBuffer();

        for (OrderDetail order : orderDetailList) {
            orders.append(order.getProductName());
            orders.append(" * ");
            orders.append(order.getProductQuantity());
            orders.append("份\n");
        }
        return orders.toString();
    }
}
