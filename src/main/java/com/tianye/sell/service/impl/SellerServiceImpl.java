package com.tianye.sell.service.impl;

import com.tianye.sell.dataobject.SellerInfo;
import com.tianye.sell.repository.SellerInfoRepository;
import com.tianye.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
