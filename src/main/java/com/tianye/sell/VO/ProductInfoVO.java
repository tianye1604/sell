package com.tianye.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @Author:tianye
* @Description:
* @Date: 15:27 2018/4/3/003
*/
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = -1238066557192891753L;
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;

}
