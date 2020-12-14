package com.fuchengbang.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fuchengbang.msm.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Auther : coach
 * @create : 2020/12/4 0004 16:22
 */
@Service
public class msmServiceImpl implements MsmService {
    private final String accessKeyId = "LTAI4G7A6KTP1XNfYvSWQxX7";
    private final String secret = "wBMaWxwBh2ZP7uEf6gPubXo11dd264";

    @Override
    public boolean send(HashMap<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName", "我的小猫教育网上教育网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode", "SMS_206560641"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
