package com.fuchengbang.orderservice.service;

import com.fuchengbang.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author coach
 * @since 2020-12-08
 */
public interface PayLogService extends IService<PayLog> {

    //生成微信支付二维码接口
    HashMap<String, Object> createNatvie(String orderNo);

    //查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //添加支付记录和更新订单状态
    void updateOrdersStatus(Map<String, String> map);
}
