package com.fuchengbang.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther : coach
 * @create : 2020/12/2 0002 15:41
 */
public interface VodService {
    String uploadVideoAly(MultipartFile file);

    void removeAlyVideo(String id);
}
