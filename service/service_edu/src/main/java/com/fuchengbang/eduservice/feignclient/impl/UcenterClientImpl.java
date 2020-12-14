package com.fuchengbang.eduservice.feignclient.impl;

import com.fuchengbang.eduservice.feignclient.UcenterClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Auther : coach
 * @create : 2020/12/8 0008 14:34
 */
@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public HashMap<String, Object> getMemberInfo(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", "获取信息失败");
        return map;
    }
}
