package com.fuchengbang.orderservice.mapper;

import com.fuchengbang.orderservice.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author coach
 * @since 2020-12-08
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
