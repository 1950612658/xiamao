package com.fuchengbang.ucenter.controller;


import com.fuchengbang.commonutils.R;
import com.fuchengbang.commonutils.UcenterMemberOrder;
import com.fuchengbang.ucenter.entity.UcenterMember;
import com.fuchengbang.ucenter.entity.vo.RegisterVo;
import com.fuchengbang.ucenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author coach
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //登陆
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        //返回token值，底层使用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.registerUser(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        UcenterMember member = memberService.getMemberInfo(request);
        return R.ok().data("userInfo", member);
    }

    //根据token获取用户信息（提供给评论模块）
    @GetMapping("getMemberInfoForComment")
    public HashMap<String, Object> getMemberInfoForComment(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        UcenterMember member = memberService.getMemberInfo(request);
        map.put("memberId", member.getId());
        map.put("nickname", member.getNickname());
        map.put("avater", member.getAvatar());
        return map;
    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //根据日期查询该天的注册人数
    @GetMapping("getRegistNo/{day}")
    public Integer getRegistNo(@PathVariable String day) {
        return memberService.getRegistNo(day);
    }
}

