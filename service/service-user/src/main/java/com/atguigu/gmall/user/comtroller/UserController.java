package com.atguigu.gmall.user.comtroller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.model.user.UserInfo;
import com.atguigu.gmall.model.vo.user.LoginSuccessVo;
import com.atguigu.gmall.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author xjrstart
 * @Date 2022-09-06-23:11
 */
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    UserInfoService userInfoService;


    // 用户登录
    @PostMapping("/passport/login")
    public Result login(@RequestBody UserInfo info){

        LoginSuccessVo vo = userInfoService.login(info);
        if(vo != null){
            return Result.ok(vo);
        }
        return Result.build("", ResultCodeEnum.LOGIN_ERROR);
    }
    // 退出登录
    @GetMapping("/passport/logout")
    public Result logout(@RequestHeader("token") String token){  // 从请求头中拿到token

        userInfoService.logout(token);
        return Result.ok();
    }

}






























