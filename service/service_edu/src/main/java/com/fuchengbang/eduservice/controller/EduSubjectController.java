package com.fuchengbang.eduservice.controller;


import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.entity.OneSubject;
import com.fuchengbang.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author coach
 * @since 2020-11-30
 */
@Api
@RestController
@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService service;

    //添加课程分类
    @ApiOperation(value = "Excel导入")
    @PostMapping("addSubject")
    public R addSubject(@RequestBody MultipartFile file) {
        //1 获取上传的excel文件 MultipartFile
        service.importSubjectData(file, service);
        return R.ok();
    }

    //
    @ApiOperation(value = "分类列表(树形)")
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> allSubjects = service.getAllOneTwoSubject();
        return R.ok().data("list", allSubjects);
    }
}

