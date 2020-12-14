package com.fuchengbang.ucenter.service;

import com.fuchengbang.ucenter.entity.UcenterMember;

/**
 * @Auther : coach
 * @create : 2020/12/7 0007 17:59
 */
public interface WxApiService {
    String getWxCode();

    UcenterMember selectMemberByOpenid(String openid);

    String callback(String code, String state);
}
