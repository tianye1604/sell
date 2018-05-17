package com.tianye.sell.dataobject.dao;

import com.tianye.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void selectByCategoryType() {

        ProductCategory productCategory = productCategoryDao.selectByCategoryType(112);
        Assert.assertEquals(112, productCategory.getCategoryType().intValue());

    }

    @Test
    public void findByCategoryType() {
        ProductCategory productCategory = productCategoryDao.findByCategoryType(112);
        Assert.assertEquals(112, productCategory.getCategoryType().intValue());
    }

    @Test
    public void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("categoryName", "营养更加均衡的粥");
        map.put("categoryType", 102);
        int result = productCategoryDao.insertByMap(map);
        Assert.assertEquals(1, result);
    }

}