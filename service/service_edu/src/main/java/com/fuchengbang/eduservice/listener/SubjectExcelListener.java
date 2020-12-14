package com.fuchengbang.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchengbang.eduservice.entity.EduSubject;
import com.fuchengbang.eduservice.entity.ExcelSubjectData;
import com.fuchengbang.eduservice.service.EduSubjectService;
import com.fuchengbang.servicebase.exception.GlobalException;

/**
 * @Auther : coach
 * @create : 2020/11/30 0030 13:55
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    private EduSubjectService service;

    public SubjectExcelListener() {
    }

    //创建有参数构造，传递subjectService用于操作数据库
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.service = subjectService;
    }

    @Override //一行一行去读取excel内容
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if (excelSubjectData == null) {
            throw new GlobalException(20001, "添加失败");
        }
        EduSubject existOne = oneExist(service, excelSubjectData.getOneSubjectName());
        //添加一级分类
        if (existOne == null) {//不存在
            EduSubject oneSubject = new EduSubject();
            oneSubject.setTitle(excelSubjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            service.save(oneSubject);
        }
        String pid = existOne.getId();
        System.out.println(pid);
        EduSubject existTwo = twoExist(service, excelSubjectData.getTwoSubjectName(), pid);
        //添加二级分类
        if (existTwo == null) {//不存在
            EduSubject oneSubject = new EduSubject();
            oneSubject.setTitle(excelSubjectData.getTwoSubjectName());
            oneSubject.setParentId(pid);
            service.save(oneSubject);
        }


    }

    @Override //读取完成后执行
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级标题是否存在
    private EduSubject oneExist(EduSubjectService service, String name) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", "0");
        EduSubject eduSubject = service.getOne(queryWrapper);
        return eduSubject;
    }

    //判断二级标题是否存在
    private EduSubject twoExist(EduSubjectService service, String name, String pid) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", pid);
        EduSubject eduSubject = service.getOne(queryWrapper);
        return eduSubject;
    }

}
