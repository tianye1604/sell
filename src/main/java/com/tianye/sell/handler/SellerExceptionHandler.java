package com.tianye.sell.handler;

import com.tianye.sell.VO.ResultVO;
import com.tianye.sell.config.ProjectUrlConfig;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.exception.SellException;
import com.tianye.sell.exception.SellerAuthorizeException;
import com.tianye.sell.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:tianye
 * @Description:
 * @Date: 19:42 2018/5/11/011
 */

@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handlerAuthorizeException() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", ResultEnum.TOKEN_INVALID.getMessage());
        String url = new StringBuffer()
//                .append("redirect:")
                .append(projectUrlConfig.getWechatOpenAuthorize())
                .append("/sell/wechat/qrAuthorize")
//                .append("/{oTgZpwXfKQA2q8Ysknz8_RRglO3A}")
                .append("?returnUrl=")
                .append(projectUrlConfig.getSell())
                .append("/sell/seller/login")
                .toString();
        map.put("url", url);
//        map.put("url", "/sell/seller/toLogin");
        return new ModelAndView("common/error", map);
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
