package com.tianye.sell.service.impl;

import com.tianye.sell.dataobject.ProductInfo;
import com.tianye.sell.dto.CartDTO;
import com.tianye.sell.enums.ProductStatusEnum;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.exception.SellException;
import com.tianye.sell.repository.ProductInfoRepository;
import com.tianye.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* @Author:tianye
* @Description:
* @Date: 10:46 2018/4/3/003
*/
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());

            if(productInfo == null) {
                log.error("【增加库存】商品信息不存在，productId:{}",cartDTO.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer stock  = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(stock);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());

            if(productInfo == null) {
                log.error("【减少库存】商品信息不存在，productId:{}",cartDTO.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer stock  = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(stock < 0) {
                log.error("【减少库存】商品库存不足，productId:{}",cartDTO.getProductId());
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(stock);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);

        if (productInfo == null) {
            log.error("【商品下架】商品信息不存在，productId:{}", productId);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN)) {
            log.error("【商品下架】商品状态不正确,productStatus={}", productInfo.getProductStatusEnum().getMessage());
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);

        if (productInfo == null) {
            log.error("【商品上架】商品信息不存在，productId:{}", productId);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if (productInfo.getProductStatus().equals(ProductStatusEnum.UP)) {
            log.error("【商品上架】商品状态不正确,productStatus={}", productInfo.getProductStatusEnum().getMessage());
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }


}
