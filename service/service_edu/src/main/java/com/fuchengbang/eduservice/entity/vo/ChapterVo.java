package com.fuchengbang.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther : coach
 * @create : 2020/12/1 0001 11:57
 */
@Data
public class ChapterVo implements Serializable {
    private String id;
    private String title;
    private List<VideoVo> children;
}
