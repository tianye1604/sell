package com.tianye.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author:tianye
 * @Description: 卖家信息
 * @Date: 17:18 2018/5/10/010
 */

@Data
@Entity
@DynamicUpdate
public class SellerInfo implements Serializable {


    private static final long serialVersionUID = 769773661591342156L;
    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

}
