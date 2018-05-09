package com.tianye.sell.service.impl;

import com.tianye.sell.dataobject.ProductCategory;
import com.tianye.sell.repository.ProductCategoryRepositroy;
import com.tianye.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepositroy repositroy;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repositroy.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repositroy.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repositroy.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(Set<Integer> typeSet) {
        return repositroy.findByCategoryTypeIn(typeSet);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repositroy.save(productCategory);
    }
}
