package com.fuchengbang.ucenter.controller;

import com.fuchengbang.ucenter.service.WxApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Auther : coach
 * @create : 2020/12/7 0007 17:27
 */
@Controller //只是请求地址，不需要返回数据
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private WxApiService wxApiService;

    // 生成微信扫描的二维码
    @GetMapping("login")
    public String getWxCode() {
        String url = wxApiService.getWxCode();
        //重定向到请求微信地址里面
        return "redirect:" + url;
    }

    //点击确认授权后执行的回调函数
    @GetMapping("callback")
    public String callback(String code, String state) {
        String jwtToken = wxApiService.callback(code, state);
        return "redirect:http://localhost:3000?token=" + jwtToken;
    }

}
