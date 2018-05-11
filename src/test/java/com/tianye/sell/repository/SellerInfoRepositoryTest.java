package com.tianye.sell.repository;

import com.tianye.sell.dataobject.SellerInfo;
import com.tianye.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setId(KeyUtil.getUniqueKey());
        sellerInfo.setOpenid(UUID.randomUUID().toString());
        sellerInfo.setUsername("tianye");
        sellerInfo.setPassword("tianyepassword");

        SellerInfo result = sellerInfoRepository.save(sellerInfo);

        Assert.assertEquals(result.getId(), sellerInfo.getId());
    }

    @Test
    public void findByOpenid() {
        SellerInfo result = sellerInfoRepository.findByOpenid("373c793f-c755-4578-ac11-9c5982a3418f");
        Assert.assertEquals("373c793f-c755-4578-ac11-9c5982a3418f", result.getOpenid());
    }
}