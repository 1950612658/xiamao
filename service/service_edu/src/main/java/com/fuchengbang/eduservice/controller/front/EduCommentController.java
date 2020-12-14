package com.fuchengbang.eduservice.controller.front;


import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.entity.EduComment;
import com.fuchengbang.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author coach
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;

    //评论的分页查询
    @GetMapping("pageComment/{current}/{limit}")
    public R pageComment(@PathVariable Long current, @PathVariable Long limit) {
        List<EduComment> eduCommentList = eduCommentService.pageComment(current, limit);
        return R.ok().data("item", eduCommentList);
    }

    @GetMapping("addComment")
    public R addComment(String courseId, String teacherId, HttpServletRequest request) {
        eduCommentService.addComment(courseId, teacherId, request);
        return R.ok();
    }


}

