package com.fuchengbang.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchengbang.commonutils.CourseWebVoOrder;
import com.fuchengbang.commonutils.UcenterMemberOrder;
import com.fuchengbang.orderservice.feignclient.EduClient;
import com.fuchengbang.orderservice.entity.Order;
import com.fuchengbang.orderservice.feignclient.UcenterClient;
import com.fuchengbang.orderservice.mapper.OrderMapper;
import com.fuchengbang.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fuchengbang.orderservice.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author coach
 * @since 2020-12-08
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 创建订单
     *
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        //通过远程调用根据课程id获取课信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }

    /**
     * 根据课程id和用户id查询订单表中的订单状态
     *
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.select("status");
        orderWrapper.eq("course_id", courseId);
        orderWrapper.eq("member_id", memberId);
        Order order = baseMapper.selectOne(orderWrapper);
        if (order != null && order.getStatus() == 1) {
            return true;//有值且状态为支付完毕
        }
        return false;
    }
}
