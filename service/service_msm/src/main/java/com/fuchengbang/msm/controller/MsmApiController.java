package com.fuchengbang.msm.controller;

import com.aliyuncs.utils.StringUtils;
import com.fuchengbang.commonutils.R;
import com.fuchengbang.msm.service.MsmService;
import com.fuchengbang.msm.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Auther : coach
 * @create : 2020/12/4 0004 16:21
 */
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin //跨域
public class MsmApiController {
    @Autowired
    private MsmService msmService;
    //redis操作类
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送短信的方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //先判断redis中有没有对应的验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {//有值
            return R.ok();
        }
        //如果redis获取不到，进行阿里云发送
        HashMap<String, Object> param = new HashMap<>();
        String fourBitCode = RandomUtil.getFourBitRandom();//生成随机的四位验证码
        param.put("code", fourBitCode);
        boolean isSend = msmService.send(param, phone);
        if (isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间为5分钟
            redisTemplate.opsForValue().set(phone, fourBitCode, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
