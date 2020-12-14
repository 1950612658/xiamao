package com.fuchengbang.staticservice.controller;


import com.fuchengbang.commonutils.R;
import com.fuchengbang.staticservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author coach
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    //得到某一天的注册人数，添加到统计表里面
    @GetMapping("registerCount/{day}")
    public R getRegistNoAndSave(@PathVariable("day") String date) {
        dailyService.getRegistNoAndSave(date);
        return R.ok();
    }

    //图表显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type, @PathVariable String begin,
                      @PathVariable String end) {
        HashMap<String, Object> map = dailyService.getShowData(type, begin, end);
        return R.ok().data(map);
    }

}

