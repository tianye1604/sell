package com.tianye.sell.dataobject;

import com.tianye.sell.enums.ProductStatusEnum;
import com.tianye.sell.utils.EnumUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* @Author:tianye
* @Description:商品信息
* @Date: 21:01 2018/4/2/002
*/
@Entity
@Data
public class ProductInfo implements Serializable {


    private static final long serialVersionUID = -8375342306257450407L;
    @Id
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 商品单价 */
    private BigDecimal productPrice;

    /** 商品库存 */
    private Integer productStock;

    /** 商品描述 */
    private String productDescription;

    /** 商品图片 */
    private String productIcon;

    /** 商品状态，0正常1下架 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 商品类目编号 */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getEnumBycode(productStatus, ProductStatusEnum.class);
    }

}
