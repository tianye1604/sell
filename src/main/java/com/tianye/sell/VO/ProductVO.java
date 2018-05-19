package com.tianye.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* @Author:tianye
* @Description:
* @Date: 15:23 2018/4/3/003
*/
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -980715569238019085L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;


}
