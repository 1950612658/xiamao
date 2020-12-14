package com.fuchengbang.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuchengbang.eduservice.entity.EduComment;
import com.fuchengbang.eduservice.feignclient.UcenterClient;
import com.fuchengbang.eduservice.mapper.EduCommentMapper;
import com.fuchengbang.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author coach
 * @since 2020-12-08
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 分页查询评论
     *
     * @param current
     * @param limit
     * @return
     */
    @Override
    public List<EduComment> pageComment(Long current, Long limit) {
        Page<EduComment> page = new Page<>(current, limit);
        baseMapper.selectPage(page, null);
        List<EduComment> eduCommentList = page.getRecords();
        return eduCommentList;
    }

    /**
     * 添加评论
     *
     * @param courseId
     * @param teacherId
     * @param request
     */
    @Override
    public void addComment(String courseId, String teacherId, HttpServletRequest request) {
        HashMap<String, Object> map = ucenterClient.getMemberInfo(request);
        String memberId = (String) map.get("memberId");
        String nickname = (String) map.get("nickname");
        String avater = (String) map.get("avater");
        EduComment comment = new EduComment();
        comment.setCourseId(courseId);
        comment.setNickname(nickname);
        comment.setAvatar(avater);
        comment.setMemberId(memberId);
        comment.setTeacherId(teacherId);
        baseMapper.insert(comment);
    }


}
