package com.fuchengbang.eduservice.feignclient;

import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.feignclient.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther : coach
 * @create : 2020/12/3 0003 10:23
 */
@Component
@FeignClient(name = "service-vod", fallback = VodClientImpl.class)
public interface VodClient {
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    R removeAlyVideo(@PathVariable("id") String id);//注意@PathVariable必须加("id")

}
