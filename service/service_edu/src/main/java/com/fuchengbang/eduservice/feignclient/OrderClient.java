package com.fuchengbang.eduservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther : coach
 * @create : 2020/12/10 0010 11:41
 */
@FeignClient(name = "service-order")
@Component
public interface OrderClient {
    //3 根据课程id和用户id查询订单表中的订单状态
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
