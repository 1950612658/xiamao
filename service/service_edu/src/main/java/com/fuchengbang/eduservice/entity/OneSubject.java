package com.fuchengbang.eduservice.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther : coach
 * @create : 2020/11/30 0030 16:24
 * 一级目录
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}
