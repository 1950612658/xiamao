package com.fuchengbang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchengbang.eduservice.entity.EduChapter;
import com.fuchengbang.eduservice.entity.EduVideo;
import com.fuchengbang.eduservice.entity.vo.ChapterVo;
import com.fuchengbang.eduservice.entity.vo.VideoVo;
import com.fuchengbang.eduservice.mapper.EduChapterMapper;
import com.fuchengbang.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fuchengbang.eduservice.service.EduVideoService;
import com.fuchengbang.servicebase.exception.GlobalException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    /**
     * 获取大纲信息
     *
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {
        List<ChapterVo> chapterVoList = new ArrayList<>();//最后封装完成的集合
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(wrapper);//根据id获取所有的章节
        for (EduChapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            String chapterId = chapter.getId();
            QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
            videoWrapper.eq("chapter_id", chapterId);
            List<EduVideo> videoList = videoService.list(videoWrapper);//根据章节id查询所有的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo video : videoList) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(video, videoVo);//将所有的信息传给vo
                videoVoList.add(videoVo);
            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }

    /**
     * 删除章节
     *
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //如果章节下有小节，不允许删除
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(videoWrapper);
        if (count != 0) {
            throw new GlobalException(20001, "删除章节失败,请先删除子节点");
        }
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("id", chapterId);
        int value = baseMapper.delete(chapterWrapper);
        return value > 0;
    }

    /**
     * 课程大纲列表,根据课程id进行查询
     *
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1 根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2 根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断：小节里面chapterid和章节里面id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    //放到小节封装集合
                    videoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

}
