package com.tianye.sell.controller;

import com.tianye.sell.config.ProjectUrlConfig;
import com.tianye.sell.constant.CookieConstant;
import com.tianye.sell.constant.RedisConstant;
import com.tianye.sell.dataobject.SellerInfo;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.service.SellerService;
import com.tianye.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {

        //1. 验证openid，是否与数据库内数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/toLogin");
            return new ModelAndView("common/error", map);
        }


        //2. 设置token到redis
        String token = UUID.randomUUID().toString();
        //key格式化为："token_uuid"
        String redisKey = String.format(RedisConstant.TOKEN_PREFIX, token);

        redisTemplate.opsForValue().set(redisKey, openid, RedisConstant.EXPIRE, TimeUnit.SECONDS);
        //3、设置token到cookie
        Cookie cookie = new Cookie(CookieConstant.TOKEN_NAME, token);
        cookie.setPath("/");
        cookie.setMaxAge(CookieConstant.MAX_AGE);
        response.addCookie(cookie);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
    }


//    @PostMapping("login")
//    public ModelAndView login(@RequestParam("openid") String openid){
//        return new ModelAndView("redirect:"
//                .concat(projectUrlConfig.getSell())
//                .concat("/sell/seller/login?")
//                .concat("openid=")
//                .concat(openid));
//    }

    /**
     * 跳转登录页面
     *
     * @return
     */
    @GetMapping("/toLogin")
    public ModelAndView toLogin() {
        return new ModelAndView("seller/login");
    }

    /**
     * 登出
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {

        //删除cookie中token
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN_NAME);

        if (cookie == null) {
            map.put("msg", ResultEnum.LOGOUT_FAIL.getMessage());
            map.put("url", "/sell/seller/toLogin");
            return new ModelAndView("common/error", map);
        }
        String token = cookie.getValue();
        CookieUtil.set(response, CookieConstant.TOKEN_NAME, null, 0);

        //删除redis中token
        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, token));

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/toLogin");

        return new ModelAndView("common/success");
    }


}
