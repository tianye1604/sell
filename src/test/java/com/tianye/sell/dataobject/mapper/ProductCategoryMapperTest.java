package com.tianye.sell.dataobject.mapper;

import com.tianye.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Test
    public void selectByCategoryType() {
        ProductCategory productCategory = productCategoryMapper.selectByCategoryType(112);
        Assert.assertEquals(112, productCategory.getCategoryType().intValue());

    }

    @Test
    public void findByCategoryType() {
    }

    @Test
    public void insertByMap() {
    }
}