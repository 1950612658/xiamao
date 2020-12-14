package com.fuchengbang.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchengbang.commonutils.JwtUtils;
import com.fuchengbang.commonutils.MD5;
import com.fuchengbang.servicebase.exception.GlobalException;
import com.fuchengbang.ucenter.entity.UcenterMember;
import com.fuchengbang.ucenter.entity.vo.RegisterVo;
import com.fuchengbang.ucenter.mapper.UcenterMemberMapper;
import com.fuchengbang.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author coach
 * @since 2020-12-07
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;//redis操作类

    /**
     * 登陆
     *
     * @param member
     * @return
     */
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GlobalException(20001, "登录失败");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(ucenterMemberQueryWrapper);
        //判断查询对象是否为空
        if (mobileMember == null) {//手机号错误
            throw new GlobalException(20001, "登陆失败");
        }
        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        String md5Password = mobileMember.getPassword();
        if (!MD5.encrypt(password).equals(md5Password)) {
            throw new GlobalException(20001, "用户名或密码错误");
        }
        //判断用户是否禁用
        Integer isDisable = mobileMember.getIsDisabled();
        if (isDisable == 1) {
            throw new GlobalException(20001, "用户已被禁用");
        }
        //登录成功
        //使用jwt工具类生成token字符串
        return JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
    }

    /**
     * 注册
     *
     * @param registerVo
     */
    @Override
    public void registerUser(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码
        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GlobalException(20001, "注册失败");
        }
        String redisCode = redisTemplate.opsForValue().get(mobile);//获取redis中存储的手机验证码
        if (!code.equals(redisCode)) {//如果不相等
            throw new GlobalException(20001, "注册失败");
        }
        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile", mobile);
        int count = baseMapper.selectCount(ucenterMemberQueryWrapper);
        if (count >= 1) {
            throw new GlobalException(20001, "手机号已存在");
        }
        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(0);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    /**
     * 根据token获取用户信息
     *
     * @param request
     * @return
     */
    @Override
    public UcenterMember getMemberInfo(HttpServletRequest request) {
        //通过JwtUtils中的方法根据request请求获取用户的id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        return baseMapper.selectById(userId);
    }

    /**
     * 根据日期获取该天的注册人数
     *
     * @param date
     * @return
     */
    @Override
    public Integer getRegistNo(String date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////        String time = sdf.format(date);//返回的日期时间
        Integer registNo = baseMapper.selectRegistNo(date);
        return registNo;
    }
}
