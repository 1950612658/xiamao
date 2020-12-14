package com.fuchengbang.eduservice.controller;


import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.entity.EduVideo;
import com.fuchengbang.eduservice.feignclient.VodClient;
import com.fuchengbang.eduservice.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author coach
 * @since 2020-12-01
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService service;
    //注入远程调用客户端
    @Autowired
    private VodClient vodClient;

    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        service.save(eduVideo);
        return R.ok();
    }

    // TODO 删除小节时候，同时把里面视频删除
    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        EduVideo video = service.getById(id);
        String videoId = video.getVideoSourceId();
        if (videoId != null) {
            vodClient.removeAlyVideo(videoId);//删除阿里云视频
        }
        service.removeById(id);
        return R.ok();
    }

    //修改小节 TODO
    @ApiOperation("修改章节")
    @PostMapping("updateVideo")
    public R updateChapter(@RequestBody EduVideo eduVideo) {
        service.updateById(eduVideo);
        return R.ok();
    }

}

