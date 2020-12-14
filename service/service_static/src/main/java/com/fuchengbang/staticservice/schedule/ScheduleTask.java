package com.fuchengbang.staticservice.schedule;

import com.fuchengbang.staticservice.service.StatisticsDailyService;
import com.fuchengbang.staticservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Auther : coach
 * @create : 2020/12/10 0010 15:51
 * 定时器任务
 */
@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService dailyService;

    //在每天凌晨1点，把前一天数据进行数据查询添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        dailyService.getRegistNoAndSave(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
