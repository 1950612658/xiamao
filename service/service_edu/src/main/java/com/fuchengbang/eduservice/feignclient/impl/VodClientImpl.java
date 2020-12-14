package com.fuchengbang.eduservice.feignclient.impl;

import com.fuchengbang.commonutils.R;
import com.fuchengbang.eduservice.feignclient.VodClient;
import org.springframework.stereotype.Component;

/**
 * @Auther : coach
 * @create : 2020/12/3 0003 14:11
 * 远程调用错误后的回调方法
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频失败");
    }
}
