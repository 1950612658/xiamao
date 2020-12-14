package com.fuchengbang.orderservice.feignclient;

import com.fuchengbang.commonutils.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther : coach
 * @create : 2020/12/8 0008 17:22
 */
@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {
    //根据用户id获取用户信息
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
