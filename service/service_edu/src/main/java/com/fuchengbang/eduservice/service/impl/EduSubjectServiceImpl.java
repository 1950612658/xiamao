package com.fuchengbang.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchengbang.eduservice.entity.EduSubject;
import com.fuchengbang.eduservice.entity.ExcelSubjectData;
import com.fuchengbang.eduservice.entity.OneSubject;
import com.fuchengbang.eduservice.entity.TwoSubject;
import com.fuchengbang.eduservice.listener.SubjectExcelListener;
import com.fuchengbang.eduservice.mapper.EduSubjectMapper;
import com.fuchengbang.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fuchengbang.servicebase.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author coach
 * @since 2020-11-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Resource
    private EduSubjectMapper mapper;

    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream fileInputStreamStream = file.getInputStream();//获取文件输入流
            EasyExcel.read(fileInputStreamStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(20001, "添加失败");
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        List<EduSubject> oneSubjects = mapper.selectList(queryWrapper);//查询所有父级目录
        ArrayList<OneSubject> oneSubjectList = new ArrayList<>();
        for (EduSubject one : oneSubjects) {
            OneSubject oneSubject = new OneSubject();
            String id = one.getId();
            oneSubject.setId(id);
            oneSubject.setTitle(one.getTitle());
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", id);
            List<EduSubject> twoSubjects = mapper.selectList(queryWrapper);
            ArrayList<TwoSubject> twoSubjectList = new ArrayList<>();
            for (EduSubject two : twoSubjects) {
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(two.getId());
                twoSubject.setTitle(two.getTitle());
                twoSubjectList.add(twoSubject);
            }
            oneSubject.setChildren(twoSubjectList);
            oneSubjectList.add(oneSubject);
        }
        return oneSubjectList;
    }
    //importSubjectData
}
