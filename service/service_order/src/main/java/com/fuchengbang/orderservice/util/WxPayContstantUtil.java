package com.fuchengbang.orderservice.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther : coach
 * @create : 2020/12/9 0009 15:23
 */
@Component
@SuppressWarnings("all")
public class WxPayContstantUtil implements InitializingBean {

    //关联的公众号appid
    @Value("${weixin.pay.appid}")
    private String appid;
    //商户号
    @Value("${weixin.pay.partner}")
    private String partner;
    //商户key
    @Value("${weixin.pay.partnerkey}")
    private String partnerkey;
    //回调地址
    @Value("${weixin.pay.notifyurl}")
    private String notifyurl;

    public static String WEIXIN_PAY_APPID;
    public static String WEIXIN_PAY_PARTNER;
    public static String WEIXIN_PAY_PARTNERKEY;
    public static String WEIXIN_PAY_NOTIFYURL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WEIXIN_PAY_APPID = appid;
        WEIXIN_PAY_PARTNER = partner;
        WEIXIN_PAY_PARTNERKEY = partnerkey;
        WEIXIN_PAY_NOTIFYURL = notifyurl;
    }
}
