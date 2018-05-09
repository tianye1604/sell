package com.tianye.sell.repository;

import com.tianye.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositroyTest {
    @Autowired
    private ProductCategoryRepositroy repository;

    @Test
    public void findOndeTest() {
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(112);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    @Transactional
    public void updateTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(1);
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(11);
        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);

        List<ProductCategory> resultList = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,resultList.size());
    }

    @Test
    public void findByCategoryTypeInSetTest(){
//        List<Integer> list = Arrays.asList(2,3,4);
        Set<Integer> typeSet = new HashSet<>(Arrays.asList(1,2,3,4));

        List<ProductCategory> resultList = repository.findByCategoryTypeIn(typeSet);
        Assert.assertNotEquals(0,resultList.size());
    }
}