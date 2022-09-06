package com.atguigu.gmall.model.vo.user;

import lombok.Data;

/**
 * @Author xjrstart
 * @Date 2022-09-06-23:21
 */
@Data
public class LoginSuccessVo {
    private String token; // 用户的令牌
    private String nickName; // 用户名
}
