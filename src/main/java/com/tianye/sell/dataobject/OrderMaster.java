package com.tianye.sell.dataobject;

import com.tianye.sell.enums.OrderStatusEnum;
import com.tianye.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
* @Author:tianye
* @Description: 订单主表
* @Date: 10:50 2018/4/4/004
*/

@Entity
@DynamicUpdate
@Data
public class OrderMaster {

    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态，默认为0是未支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;


}
