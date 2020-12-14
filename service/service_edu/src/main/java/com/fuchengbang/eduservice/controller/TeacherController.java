package com.fuchengbang.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.entity.Teacher;
import com.fuchengbang.eduservice.entity.vo.TeacherCondition;
import com.fuchengbang.eduservice.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author coach
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin//解决跨域问题
@Api(produces = "讲师管理")
public class TeacherController {
    @Autowired
    private TeacherService service;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R getTeacherList() {
//        try {
//            int i = 10 / 0;
//        }catch (Exception e){
//            throw new GlobalException(400,"出现自定义异常");
//        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", service.list(null));
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping(value = "/{id}")
    public R DeleteTeacherById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id) {
        if (service.removeById(id)) {
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }

    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current", value = "当前页数", required = true)
                             @PathVariable long current,
                             @ApiParam(name = "limit", value = "每一页的条数", required = true)
                             @PathVariable long limit) {
        Page<Teacher> page = new Page<>();
        service.page(page, null);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("rows", page.getRecords());
        return R.ok().data(map);
    }

    @ApiOperation(value = "分页条件查询讲师")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R selectPages(@ApiParam(name = "current", value = "当前页数", required = true) @PathVariable Long current,
                         @ApiParam(name = "limit", value = "每一页的条数", required = true) @PathVariable Long limit,
                         @ApiParam(name = "teacherCondition", value = "查询条件", required = false)
                         @RequestBody TeacherCondition teacherCondition) {
        Page<Teacher> page = new Page<>(current, limit);
        service.pageQuery(page, teacherCondition);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("rows", page.getRecords());
        return R.ok().data(map);

    }

    @ApiOperation(value = "新增教师")
    @PostMapping("/save")
    public R save(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                  @RequestBody Teacher teacher) {
        service.save(teacher);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        Teacher teacher = service.getById(id);
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("item", teacher);
//        return R.ok().data(map);
        return R.ok().data("teacher", teacher);
    }

    @ApiOperation(value = "更新讲师信息")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher) {
        boolean flag = service.updateById(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

