package com.fuchengbang.eduservice.feignclient;

import com.fuchengbang.eduservice.feignclient.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Auther : coach
 * @create : 2020/12/8 0008 14:33
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {
    //根据token获取用户信息
    @GetMapping("/educenter/member/getMemberInfoForComment")
    HashMap<String, Object> getMemberInfo(HttpServletRequest request);
}
