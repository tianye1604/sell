package com.tianye.sell.service;

import com.tianye.sell.dataobject.ProductCategory;

import java.util.List;
import java.util.Set;

/**
* @Author:tianye
* @Description:
* @Date: 18:10 2018/4/2/002
*/
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    List<ProductCategory>findByCategoryTypeIn(Set<Integer> typeSet);

    ProductCategory save(ProductCategory productCategory);

    ProductCategory selectByCategoryType(Integer categoryType);

}
