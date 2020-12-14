package com.fuchengbang.ucenter.mapper;

import com.fuchengbang.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author coach
 * @since 2020-12-07
 */
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //查询当天的注册人数
    @Select("SELECT COUNT(*) FROM ucenter_member um WHERE DATE(um.gmt_create) = #{date}")
    Integer selectRegistNo(@Param("date") String date);

}
