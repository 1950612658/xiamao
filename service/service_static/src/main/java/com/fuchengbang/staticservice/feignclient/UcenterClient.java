package com.fuchengbang.staticservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @Auther : coach
 * @create : 2020/12/10 0010 13:24
 */
@FeignClient(name = "service-ucenter")
@Component
public interface UcenterClient {
    //根据日期查询该天的注册人数
    @GetMapping("/educenter/member/getRegistNo/{day}")
    Integer getRegistNo(@PathVariable("day") String date);
}
