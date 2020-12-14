package com.fuchengbang.eduservice.controller;


import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.entity.EduCourse;
import com.fuchengbang.eduservice.entity.vo.CourseInfoForm;
import com.fuchengbang.eduservice.entity.vo.CoursePublishVo;
import com.fuchengbang.eduservice.entity.vo.CourseQuery;
import com.fuchengbang.eduservice.entity.vo.TeacherCondition;
import com.fuchengbang.eduservice.feignclient.VodClient;
import com.fuchengbang.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author coach
 * @since 2020-12-01
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService service;

    //课程列表 基本实现
    //TODO  完善条件查询带分页
    @ApiOperation("获取课程列表")
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = service.list(null);
        return R.ok().data("list", list);
    }

    @ApiOperation("添加课程基本信息的方法")
    @PostMapping("/addCourseInfo")
    public R getCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String id = service.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId", id);
    }

    @ApiOperation("根据id查询课程基本信息")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoForm courseInfo = service.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfo);
    }

    @ApiOperation("修改课程数据")
    @PostMapping("/UpdateCourseInfo")
    public R UpdateCourseInfo(@RequestBody CourseInfoForm courseInfo) {
        service.UpdateCourseInfo(courseInfo);
        return R.ok();
    }

    @ApiOperation("根据课程id查询课程确认信息")
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = service.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    @ApiOperation("课程最终发布")
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");//设置课程发布状态
        service.updateById(course);
        return R.ok();
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        service.removesCourse(courseId);
        return R.ok();
    }

    @ApiOperation("根据条件分页查询课程列表")
    @PostMapping("/getPageCourse/{current}/{limit}")
    public R getPageCourse(@ApiParam(name = "current", value = "当前页数", required = true) @PathVariable Long current,
                           @ApiParam(name = "limit", value = "每一页的条数", required = true) @PathVariable Long limit,
                           @ApiParam(name = "courseQuery", value = "查询条件") @RequestBody(required = false) CourseQuery courseQuery) {
        List<EduCourse> courseList = service.getPageCourse(current, limit, courseQuery);
        return R.ok().data("list", courseList);
    }

}

