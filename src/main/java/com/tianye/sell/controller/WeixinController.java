package com.tianye.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
* @Author:tianye
* @Description:
* @Date: 18:20 2018/4/16/016
*/

@RestController
@Slf4j
@RequestMapping("/weixin")
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code,
                     @RequestParam("state") String state){
        log.info("进入微信auth……");
        log.info("code:{}",code);
        log.info("state:{}",state);

        RestTemplate template = new RestTemplate();
        String url1 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx00d5772bc0058d9b&secret=5d13ed8ff75b1288905c800e9f698e57&code="+
                code +"&grant_type=authorization_code";
        String response = template.getForObject(url1,String.class);
        log.info("response:{}",response);

    }
}
