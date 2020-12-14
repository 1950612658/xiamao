package com.fuchengbang.eduservice.service;

import com.fuchengbang.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author coach
 * @since 2020-12-08
 */
public interface EduCommentService extends IService<EduComment> {


    List<EduComment> pageComment(Long current, Long limit);

    void addComment(String courseId, String teacherId, HttpServletRequest request);
}
