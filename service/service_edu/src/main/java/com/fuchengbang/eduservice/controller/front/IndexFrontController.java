package com.fuchengbang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.entity.EduCourse;
import com.fuchengbang.eduservice.entity.Teacher;
import com.fuchengbang.eduservice.service.EduCourseService;
import com.fuchengbang.eduservice.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther : coach
 * @create : 2020/12/4 0004 9:54
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {
    //
    @Autowired
    private EduCourseService courseService;
    @Resource
    private TeacherService teacherService;

    @ApiOperation("查询前八条热门课程 查询前四名讲师")
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @GetMapping("index")
    public R selectIndex() {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(courseQueryWrapper);
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("level");
        teacherQueryWrapper.last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("eduList", courseList);
        map.put("teacherList", teacherList);
        return R.ok().data(map);
    }
}
