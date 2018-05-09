package com.tianye.sell.service;

import com.tianye.sell.dataobject.ProductInfo;
import com.tianye.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
* @Author:tianye
* @Description:
* @Date: 10:42 2018/4/3/003
*/

public interface ProductService {

    ProductInfo findOne(String productId);

    //查询所有在架的商品
    List<ProductInfo> findUpAll();

    //分页查询
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

}
