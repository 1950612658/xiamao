package com.fuchengbang.servicebase.handler;

import com.fuchengbang.commonutils.ExceptionUtil;
import com.fuchengbang.commonutils.R;
import com.fuchengbang.servicebase.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther : coach
 * @create : 2020/11/27 0027 9:43
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public R error(GlobalException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
