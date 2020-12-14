package com.fuchengbang.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.fuchengbang.ossservice.service.OssService;
import com.fuchengbang.ossservice.util.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @Auther : coach
 * @create : 2020/11/27 0027 17:59
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String upload(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String randomId = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = file.getOriginalFilename();
        String objectName = randomId + fileName;
        //https://xiaomao-edu.oss-cn-beijing.aliyuncs.com/0.jpg
        String fileUrl = "https://" + bucketName + "." + endpoint + "/" + objectName;
        ossClient.putObject("xiaomao-edu", objectName, inputStream);

// 关闭OSSClient。
        ossClient.shutdown();
        return fileUrl;
    }
}
