package com.fuchengbang.ucenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchengbang.commonutils.JwtUtils;
import com.fuchengbang.servicebase.exception.GlobalException;
import com.fuchengbang.ucenter.entity.UcenterMember;
import com.fuchengbang.ucenter.service.UcenterMemberService;
import com.fuchengbang.ucenter.service.WxApiService;
import com.fuchengbang.ucenter.util.HttpClientUtils;
import com.fuchengbang.ucenter.util.WxConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Auther : coach
 * @create : 2020/12/7 0007 17:59
 */
@Service
public class WxApiServiceImpl implements WxApiService {

    @Autowired
    private UcenterMemberService memberService;
    @Autowired
    private WxApiService wxApiService;

    private UcenterMember member;

    /**
     * 获取微信code码
     *
     * @return
     */
    @Override
    public String getWxCode() {
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" + //%s相当于?代表占位符
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URLEncoder编码
        String redirectUrl = WxConstantUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置%s里面值
        String url = String.format(
                baseUrl,
                WxConstantUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "xiaomao"
        );
        return url;
    }

    /**
     * 通过openid查询是否有对应的用户
     *
     * @param openid
     * @return
     */
    @Override
    public UcenterMember selectMemberByOpenid(String openid) {
        QueryWrapper<UcenterMember> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("openid", openid);
        UcenterMember member = memberService.getOne(memberQueryWrapper);
        return member;
    }

    /**
     * 点击确认授权后执行的回调函数,返回jwt
     *
     * @param code
     * @param state
     * @return
     */
    @Override
    public String callback(String code, String state) {
        //获取code值，临时票据，类似于验证码
        //拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //拼接三个参数 ：id  秘钥 和 code值
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                WxConstantUtil.WX_OPEN_APP_ID,
                WxConstantUtil.WX_OPEN_APP_SECRET,
                code
        );
        //请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
        //使用httpclient发送请求，得到返回结果,里面包含access_token 和openid
        try {
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            JSONObject accessTokenJson = JSONObject.parseObject(accessTokenInfo);
            String access_token = accessTokenJson.getString("access_token");
            String openid = accessTokenJson.getString("openid");
            member = wxApiService.selectMemberByOpenid(openid);
            if (member == null) {//表示不存在
                //拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //HttpClient发送请求获取用户信息
                String userInfo = HttpClientUtils.get(userInfoUrl);
                JSONObject userInfoObject = JSONObject.parseObject(userInfo);
                String nickname = userInfoObject.getString("nickname");//昵称
                String headimgurl = userInfoObject.getString("headimgurl");//头像
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            //使用jwt根据member对象生成token字符串
            System.out.println(member.getId() + "===" + member.getNickname());
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //最后：返回首页面，通过路径传递token字符串
            return jwtToken;
        } catch (Exception e) {
            throw new GlobalException(20001, "登录失败");
        }
    }
}
