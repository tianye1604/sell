package com.tianye.sell.controller;

import com.lly835.bestpay.utils.JsonUtil;
import com.tianye.sell.config.ProjectUrlConfig;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
* @Author:tianye
* @Description:
* @Date: 12:42 2018/4/17/017
*/
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private WxMpService wxOpenService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url = "http://7dian.nat300.top/sell/wechat/userinfo";
        String redirectURI = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("redirectURI:{}", JsonUtil.toJson(redirectURI));

        return "redirect:" + redirectURI;

    }

    @GetMapping("/userinfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){
        log.info("进入userinfo……");
        log.info("code:{},returnUrl:{}",code,returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try{
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.error("【微信网页授权】失败：{}",e);
            throw new SellException(ResultEnum.WECHAT_AUTH_ERROR.getCode(),e.getMessage());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" +openId;
    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
        String url = projectUrlConfig.getWechatOpenAuthorize() + "/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url,
                WxConsts.QrConnectScope.SNSAPI_LOGIN,
                URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl) {
        log.info("code:{},returnUrl:{}", code, returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信开放平台登录】失败：{}", e);
            throw new SellException(ResultEnum.WECHAT_AUTH_ERROR.getCode(), e.getMessage());
        }
        log.info("wxMpOAuth2AcessToken={}", wxMpOAuth2AccessToken);
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" + openId;
    }
}
