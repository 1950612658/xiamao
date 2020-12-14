package com.fuchengbang.ossservice.controller;

import com.fuchengbang.commonutils.R;
import com.fuchengbang.ossservice.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther : coach
 * @create : 2020/11/27 0027 17:48
 */
@Api
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation(value = "上传头像")
    @PostMapping
    public R uploadOssFile(@ApiParam(name = "file", value = "需要上传的文件")
                           @RequestParam("file") MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.upload(file);
        return R.ok().data("url", url);
    }
}
