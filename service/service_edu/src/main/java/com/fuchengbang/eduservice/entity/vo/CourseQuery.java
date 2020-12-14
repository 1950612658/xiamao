package com.fuchengbang.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther : coach
 * @create : 2020/12/2 0002 10:11
 */
@ApiModel(value = "Course查询条件")
@Data
public class CourseQuery {
    private static final long serialVersionUID = 1L;
    private String title;
    private String status;
}
