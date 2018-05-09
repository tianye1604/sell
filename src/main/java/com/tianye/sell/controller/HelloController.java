package com.tianye.sell.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Author:tianye
* @Description:
* @Date: 15:39 2018/5/8/008
*/
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/list")
    public String list(){
        return "order/list";
    }
}
