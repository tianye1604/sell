package com.tianye.sell.repository;

import com.tianye.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
* @Author:tianye
* @Description:
* @Date: 12:16 2018/4/2/002
*/
public interface ProductCategoryRepositroy extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    List<ProductCategory> findByCategoryTypeIn(Set<Integer> typeSet);
}
