package com.fuchengbang.eduservice.service;

import com.fuchengbang.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fuchengbang.eduservice.entity.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author coach
 * @since 2020-11-30
 */
public interface EduSubjectService extends IService<EduSubject> {
    //上传excel文件提取数据
    void importSubjectData(MultipartFile file, EduSubjectService subjectService);

    //获取所有的subject(树形结构)
    List<OneSubject> getAllOneTwoSubject();
}
