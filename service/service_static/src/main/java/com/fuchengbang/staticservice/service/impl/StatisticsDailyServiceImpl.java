package com.fuchengbang.staticservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchengbang.staticservice.entity.StatisticsDaily;
import com.fuchengbang.staticservice.feignclient.UcenterClient;
import com.fuchengbang.staticservice.mapper.StatisticsDailyMapper;
import com.fuchengbang.staticservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author coach
 * @since 2020-12-10
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void getRegistNoAndSave(String date) {
        //先查询到有关记录并删除掉这一行记录
        QueryWrapper<StatisticsDaily> dailyWrapper = new QueryWrapper<>();
        dailyWrapper.eq("date_calculated", date);
        baseMapper.delete(dailyWrapper);//删除这行记录
        //远程调用获取注册人数
        Integer registNo = ucenterClient.getRegistNo(date);
        //重新存入统计数据库中
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registNo);//注册人数
        statisticsDaily.setDateCalculated(date);//统计日期
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));//每日播放视频数
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));//每日新增课程数
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));//登录人数
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public HashMap<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        //因为返回有两部分数据：日期 和 日期对应数量
        //前端要求数组json结构，对应后端java代码是list集合
        //创建两个list集合，一个日期list，一个数量list
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        //遍历查询所有数据list集合，进行封装
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            //封装日期list集合
            date_calculatedList.add(daily.getDateCalculated());
            //封装对应数量
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        //把封装之后两个list集合放到map集合，进行返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("numDataList", numDataList);
        return map;
    }
}
