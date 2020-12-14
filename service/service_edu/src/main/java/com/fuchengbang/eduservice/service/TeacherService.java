package com.fuchengbang.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuchengbang.eduservice.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fuchengbang.eduservice.entity.vo.TeacherCondition;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author coach
 * @since 2020-11-26
 */
public interface TeacherService extends IService<Teacher> {
    void pageQuery(Page<Teacher> pageParam, TeacherCondition teacherCondition);

    HashMap<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher);
}
