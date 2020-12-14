package com.fuchengbang.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.fuchengbang.commonutils.R;
import com.fuchengbang.servicebase.exception.GlobalException;
import com.fuchengbang.vodservice.service.VodService;
import com.fuchengbang.vodservice.util.ConstantVodUtils;
import com.fuchengbang.vodservice.util.InitVodCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther : coach
 * @create : 2020/12/2 0002 15:40
 */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService service;

    //上传视频到阿里云
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = service.uploadVideoAly(file);
        return R.ok().data("videoId", videoId);
    }

    //根据视频id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        service.removeAlyVideo(id);
        return R.ok();
    }

}
