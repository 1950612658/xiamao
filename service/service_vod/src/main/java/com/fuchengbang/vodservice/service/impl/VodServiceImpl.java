package com.fuchengbang.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.fuchengbang.servicebase.exception.GlobalException;
import com.fuchengbang.vodservice.service.VodService;
import com.fuchengbang.vodservice.util.ConstantVodUtils;
import com.fuchengbang.vodservice.util.InitVodCilent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Auther : coach
 * @create : 2020/12/2 0002 15:41
 */
@Service
public class VodServiceImpl implements VodService {

    /**
     * 上传阿里云视频
     *
     * @param file
     * @return
     */
    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            String accessKeyId = ConstantVodUtils.ACCESS_KEY_ID;
            String accessKeySecret = ConstantVodUtils.ACCESS_KEY_SECRET;
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();
            return videoId;
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 删除阿里云视频
     *
     * @param id
     */
    @Override
    public void removeAlyVideo(String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(20001, "删除视频失败");
        }
    }
}
