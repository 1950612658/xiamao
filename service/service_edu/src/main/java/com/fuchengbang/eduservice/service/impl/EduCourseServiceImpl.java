package com.fuchengbang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuchengbang.eduservice.entity.EduChapter;
import com.fuchengbang.eduservice.entity.EduCourse;
import com.fuchengbang.eduservice.entity.EduCourseDescription;
import com.fuchengbang.eduservice.entity.EduVideo;
import com.fuchengbang.eduservice.entity.frontvo.CourseFrontVo;
import com.fuchengbang.eduservice.entity.frontvo.CourseWebVo;
import com.fuchengbang.eduservice.entity.vo.CourseInfoForm;
import com.fuchengbang.eduservice.entity.vo.CoursePublishVo;
import com.fuchengbang.eduservice.entity.vo.CourseQuery;
import com.fuchengbang.eduservice.feignclient.VodClient;
import com.fuchengbang.eduservice.mapper.EduCourseMapper;
import com.fuchengbang.eduservice.service.EduChapterService;
import com.fuchengbang.eduservice.service.EduCourseDescriptionService;
import com.fuchengbang.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fuchengbang.eduservice.service.EduVideoService;
import com.fuchengbang.servicebase.exception.GlobalException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author coach
 * @since 2020-12-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;//注入远程调用客户端

    /**
     * 获取表单中的所有信息并存储起来
     */
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        int insert = baseMapper.insert(eduCourse);//成功添加的记录数 0失败 1成功
        if (insert == 0) {
            throw new GlobalException(20001, "添加课程信息失败");
        }
        String id = eduCourse.getId();
        EduCourseDescription description = new EduCourseDescription();
        description.setId(id);
        description.setDescription(courseInfoForm.getDescription());
        descriptionService.save(description);
        return id;
    }

    /**
     * 根据id返回表单中的所有信息
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoForm getCourseInfo(String courseId) {
        EduCourse course = baseMapper.selectById(courseId);
        CourseInfoForm form = new CourseInfoForm();
        BeanUtils.copyProperties(course, form);
        EduCourseDescription description = descriptionService.getById(courseId);
        form.setDescription(description.getDescription());
        return form;
    }

    /**
     * 修改课程信息
     *
     * @param courseInfo
     */
    @Override
    public void UpdateCourseInfo(CourseInfoForm courseInfo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfo, course);
        int update = baseMapper.updateById(course);
        if (update == 0) {
            throw new GlobalException(20001, "修改课程信息失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfo.getId());
        description.setDescription(courseInfo.getDescription());
        descriptionService.updateById(description);
    }

    /**
     * 最终发布课程
     *
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo coursePublishVo = baseMapper.publishCourseInfo(id);
        return coursePublishVo;
    }

    /**
     * 删除课程
     *
     * @param courseId
     */
    @Override
    public void removesCourse(String courseId) {
        //删除小节以及里面的所有视频
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        //查询课程里的所有章节
        List<EduChapter> chapterList = chapterService.list(chapterQueryWrapper);
        for (EduChapter chapter : chapterList) {
            //获取每一个章节id
            String chapterId = chapter.getId();
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("chapter_id", chapterId);
            //根据章节id查询所有的小节
            List<EduVideo> videoList = videoService.list(videoQueryWrapper);
            for (EduVideo video : videoList) {//遍历所有的video
                //先获取阿里云视频id
                String videoId = video.getVideoSourceId();
                if (videoId != null) {
                    //根据视频id删除视频
                    vodClient.removeAlyVideo(videoId);
                }
                //删除小节
                videoService.removeById(video);
            }
        }
        //删除章节
        QueryWrapper<EduChapter> chapterQueryWrapper1 = new QueryWrapper<>();
        chapterQueryWrapper1.eq("course_id", courseId);
        chapterService.remove(chapterQueryWrapper1);
        //删除描述
        descriptionService.removeById(courseId);
        //删除课程
        int count = baseMapper.deleteById(courseId);
        if (count == 0) {
            throw new GlobalException(20001, "删除课程失败");
        }
    }

    /**
     * 根据条件分页查询课程列表
     *
     * @return
     */
    @Override
    public List<EduCourse> getPageCourse(Long current, Long limit, CourseQuery courseQuery) {
        Page<EduCourse> coursePage = new Page<>(current, limit);
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        if (title != null) {
            courseQueryWrapper.like("title", title);
        }
        if (status != null) {
            courseQueryWrapper.eq("status", status);
        }
        return baseMapper.selectPage(coursePage, courseQueryWrapper).getRecords();
    }

    /**
     * 条件查询带分页查询课程
     *
     * @param pageParam
     * @param courseFrontVo
     * @return
     */
    @Override
    public HashMap<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    //根据课程id，编写sql语句查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }


}
