package com.fuchengbang.eduservice.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Auther : coach
 * @create : 2020/11/30 0030 13:41
 * 和excel对应的实体类
 */
@Data
public class ExcelSubjectData {
    //父科目名称
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    //子科目名称
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
