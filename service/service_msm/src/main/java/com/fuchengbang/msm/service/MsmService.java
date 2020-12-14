package com.fuchengbang.msm.service;

import com.fuchengbang.commonutils.R;

import java.util.HashMap;

/**
 * @Auther : coach
 * @create : 2020/12/4 0004 16:21
 */
public interface MsmService {
    boolean send(HashMap<String, Object> param, String phone);
}
