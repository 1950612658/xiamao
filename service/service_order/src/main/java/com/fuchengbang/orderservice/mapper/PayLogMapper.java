package com.fuchengbang.orderservice.mapper;

import com.fuchengbang.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付日志表 Mapper 接口
 * </p>
 *
 * @author coach
 * @since 2020-12-08
 */
@Mapper
public interface PayLogMapper extends BaseMapper<PayLog> {

}
