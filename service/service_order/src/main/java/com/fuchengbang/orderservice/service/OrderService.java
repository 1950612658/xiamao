package com.fuchengbang.orderservice.service;

import com.fuchengbang.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author coach
 * @since 2020-12-08
 */
public interface OrderService extends IService<Order> {

    //创建订单
    String createOrders(String courseId, String memberIdByJwtToken);

    //判断用户是否买了课程
    boolean isBuyCourse(String courseId, String memberId);
}
