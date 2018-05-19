package com.tianye.sell.service.impl;

import com.tianye.sell.dataobject.ProductCategory;
import com.tianye.sell.dataobject.dao.ProductCategoryDao;
import com.tianye.sell.repository.ProductCategoryRepositroy;
import com.tianye.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "productCategory")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepositroy repositroy;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    @Cacheable(key = "#categoryId")
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
    @CachePut(key = "#productCategory.categoryId")
    public ProductCategory save(ProductCategory productCategory) {
        return repositroy.save(productCategory);
    }

    @Override
    public ProductCategory selectByCategoryType(Integer categoryType) {
        return productCategoryDao.selectByCategoryType(categoryType);
    }


}
