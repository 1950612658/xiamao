package com.fuchengbang.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuchengbang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fuchengbang.eduservice.entity.frontvo.CourseFrontVo;
import com.fuchengbang.eduservice.entity.frontvo.CourseWebVo;
import com.fuchengbang.eduservice.entity.vo.CourseInfoForm;
import com.fuchengbang.eduservice.entity.vo.CoursePublishVo;
import com.fuchengbang.eduservice.entity.vo.CourseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author coach
 * @since 2020-12-01
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfo(String courseId);

    void UpdateCourseInfo(CourseInfoForm courseInfo);

    CoursePublishVo publishCourseInfo(String id);

    void removesCourse(String courseId);

    List<EduCourse> getPageCourse(Long current, Long limit, CourseQuery courseQuery);

    HashMap<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
