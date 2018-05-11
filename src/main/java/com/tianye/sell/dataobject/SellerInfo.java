package com.tianye.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author:tianye
 * @Description: 卖家信息
 * @Date: 17:18 2018/5/10/010
 */

@Data
@Entity
@DynamicUpdate
public class SellerInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

}
