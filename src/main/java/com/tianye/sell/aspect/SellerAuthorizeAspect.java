package com.tianye.sell.aspect;


import com.tianye.sell.constant.CookieConstant;
import com.tianye.sell.constant.RedisConstant;
import com.tianye.sell.exception.SellerAuthorizeException;
import com.tianye.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Pointcut("execution(public * com.tianye.sell.controller.Seller*.*(..))" +
            "&& !execution(public * com.tianye.sell.controller.SellerUserController.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doVerify() {
        //取到cookie，查询cookie
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN_NAME);

        //判断cookie中信息
        if (cookie == null) {
            log.warn("【登录检验】，cookie中未查询到token");
            throw new SellerAuthorizeException();
        }

        //查询redis中信息，并判断
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录检验】，Redis中查找不到相应的token");
            throw new SellerAuthorizeException();
        }
    }
}
