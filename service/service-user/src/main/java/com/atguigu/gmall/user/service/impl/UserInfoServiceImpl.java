package com.atguigu.gmall.user.service.impl;


import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.common.util.MD5;
import com.atguigu.gmall.model.user.UserInfo;
import com.atguigu.gmall.model.vo.user.LoginSuccessVo;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import com.atguigu.gmall.user.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 86136
 * @description 针对表【user_info(用户表)】的数据库操作Service实现
 * @createDate 2022-09-06 22:58:02
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    // 用户登录
    @Override
    public LoginSuccessVo login(UserInfo info) {

        LoginSuccessVo vo = new LoginSuccessVo();

        // 查数据库有没有这个用户
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getName, info.getLoginName())
                .eq(UserInfo::getPasswd, MD5.encrypt(info.getPasswd()));
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        // 判断
        if (userInfo != null) { // 说明数据库中有，登录成功
            String token = UUID.randomUUID().toString().replace("-", "");
            vo.setToken(token);

            redisTemplate.opsForValue().set(
                    SysRedisConst.LOGIN_USER + token,
                    Jsons.toStr(userInfo),
                    7,
                    TimeUnit.DAYS);
            vo.setToken(token);
            vo.setNickName(userInfo.getNickName());
            return vo;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}




























