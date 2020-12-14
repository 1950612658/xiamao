package com.fuchengbang.eduservice.service;

import com.fuchengbang.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fuchengbang.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author coach
 * @since 2020-12-01
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String courseId);

    boolean deleteChapter(String chapterId);

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
