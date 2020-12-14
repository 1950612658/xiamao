package com.fuchengbang.eduservice.controller;


import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.entity.EduChapter;
import com.fuchengbang.eduservice.entity.vo.ChapterVo;
import com.fuchengbang.eduservice.service.EduChapterService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService service;

    @ApiOperation("课程大纲列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> chapterList = service.getChapterVideo(courseId);
        return R.ok().data("allChapterVideo", chapterList);
    }

    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        service.save(eduChapter);
        return R.ok();
    }

    @ApiOperation("根据章节id查询")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = service.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        service.updateById(eduChapter);
        return R.ok();
    }

    @ApiOperation("删除的方法")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = service.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }
}

