package com.tianye.sell.dataobject.dao;

import com.tianye.sell.dataobject.ProductCategory;
import com.tianye.sell.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @Author:tianye
 * @Description:
 * @Date: 16:53 2018/5/15/015
 */
@Repository
public class ProductCategoryDao {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    public ProductCategory selectByCategoryType(Integer categoryType) {
        return productCategoryMapper.selectByCategoryType(categoryType);
    }

    public ProductCategory findByCategoryType(Integer categoryType) {
        return productCategoryMapper.findByCategoryType(categoryType);
    }

    public int insertByMap(Map<String, Object> map) {
        return productCategoryMapper.insertByMap(map);
    }

}
