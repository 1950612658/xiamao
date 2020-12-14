package com.fuchengbang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.entity.Teacher;
import com.fuchengbang.eduservice.entity.vo.TeacherCondition;
import com.fuchengbang.eduservice.mapper.TeacherMapper;
import com.fuchengbang.eduservice.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author coach
 * @since 2020-11-26
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public void pageQuery(Page<Teacher> pageParam, TeacherCondition teacherCondition) {
        if (teacherCondition == null) {
            return;
        }
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        String name = teacherCondition.getName();
        Integer level = teacherCondition.getLevel();
        String begin = teacherCondition.getBegin();
        String end = teacherCondition.getEnd();
        if (name != null) {
            queryWrapper.like("name", name);
        }
        if (level != null) {
            queryWrapper.eq("level", level);
        }
        if (begin != null) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (end != null) {
            queryWrapper.le("gmt_create", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    //1 分页查询讲师的方法
    @Override
    public HashMap<String, Object> getTeacherFrontList(Page<Teacher> pageParam) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageParam, wrapper);

        List<Teacher> records = pageParam.getRecords();
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
}
