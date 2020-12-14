package com.fuchengbang.ucenter.service;

import com.fuchengbang.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fuchengbang.ucenter.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author coach
 * @since 2020-12-07
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void registerUser(RegisterVo registerVo);

    UcenterMember getMemberInfo(HttpServletRequest request);

    Integer getRegistNo(String date);
}
