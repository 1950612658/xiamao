package com.fuchengbang.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther : coach
 * @create : 2020/11/27 0027 17:48
 */
public interface OssService {
    /**
     * 上传至阿里云
     *
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
