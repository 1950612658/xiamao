package com.fuchengbang.staticservice.service;

import com.fuchengbang.staticservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author coach
 * @since 2020-12-10
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getRegistNoAndSave(String date);

    HashMap<String, Object> getShowData(String type, String begin, String end);
}
