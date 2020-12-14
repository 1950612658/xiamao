package com.fuchengbang.orderservice.feignclient;

import com.fuchengbang.commonutils.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther : coach
 * @create : 2020/12/8 0008 17:20
 */
@FeignClient(name = "service-edu")
@Component
public interface EduClient {
    //根据课程id查询课程信息
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
