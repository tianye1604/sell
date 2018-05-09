package com.tianye.sell.repository;

import com.tianye.sell.dataobject.OrderDetail;
import com.tianye.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
* @Author:tianye
* @Description:
* @Date: 11:46 2018/4/4/004
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(KeyUtil.getUniqueKey());
        orderDetail.setOrderId("1522816593056230873");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductId("100002");
        orderDetail.setProductName("小米南瓜粥");
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductQuantity(3);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("1522816593056230873");
        Assert.assertNotEquals(0,orderDetailList.size());

    }
}