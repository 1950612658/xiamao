package com.fuchengbang.eduservice.controller.front;


import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuchengbang.commonutils.CourseWebVoOrder;
import com.fuchengbang.commonutils.JwtUtils;
import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.entity.EduCourse;
import com.fuchengbang.eduservice.entity.frontvo.CourseFrontVo;
import com.fuchengbang.eduservice.entity.frontvo.CourseWebVo;
import com.fuchengbang.eduservice.entity.vo.ChapterVo;
import com.fuchengbang.eduservice.feignclient.OrderClient;
import com.fuchengbang.eduservice.service.EduChapterService;
import com.fuchengbang.eduservice.service.EduCourseService;
import com.fuchengbang.servicebase.exception.GlobalException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    //1 条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        HashMap<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //根据课程id和用户id查询当前课程是否已经支付过了
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            throw new GlobalException(20001, "请先进行登陆");
        }
        boolean isBuy = orderClient.isBuyCourse(courseId, memberId);

        //封装数据传给前端
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseWebVo", courseWebVo);
        map.put("chapterVideoList", chapterVideoList);
        map.put("isBuy", isBuy);
        return R.ok().data(map);
    }

    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo, courseWebVoOrder);
        return courseWebVoOrder;
    }

}












