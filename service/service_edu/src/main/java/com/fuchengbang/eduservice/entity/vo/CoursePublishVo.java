package com.fuchengbang.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther : coach
 * @create : 2020/12/1 0001 15:21
 */
@Data
public class CoursePublishVo implements Serializable {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}