package com.songshilong.service.user.service;

import com.songshilong.service.user.dto.request.PasswordMailResetRequest;
import com.songshilong.service.user.dto.request.ResetPasswordRequest;
import com.songshilong.service.user.dto.request.UserLoginRequest;
import com.songshilong.service.user.dto.request.UserRegisterRequest;
import com.songshilong.service.user.dto.response.PasswordMailResetResponse;
import com.songshilong.service.user.dto.response.UserLoginResponse;
import com.songshilong.service.user.dto.response.UserRegisterResponse;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.service.impl
 * @Author: Ice, Song
 * @CreateTime: 2025-03-26  20:29
 * @Description: UserService
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 注册
     * @param userRegisterRequest {@link UserRegisterRequest} 注册需要的用户数据
     * @return {@link UserRegisterResponse} 注册结果
     */
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    /**
     * 登录
     * @param userLoginRequest {@link UserLoginRequest} user login request param
     * @return {@link UserLoginResponse} login response
     */
    UserLoginResponse login(UserLoginRequest userLoginRequest);

    /**
     * 判断用户名是否已经存在
     * @param username 用户名
     * @return true-已经存在 false-不存在
     */
    Boolean hasUsername(String username);

    /**
     *  获取密码重置邮箱验证码
     * @param passwordMailResetRequest  {@link PasswordMailResetRequest} 获取邮箱验证码的请求参数
     * @return {@link PasswordMailResetResponse} 响应结果
     */
    PasswordMailResetResponse getPasswordMailResetCode(PasswordMailResetRequest passwordMailResetRequest);

    /**
     * 通过邮件验证码重置密码
     * @param resetPasswordRequest {@link ResetPasswordRequest} 重置密码的请求参数
     * @return true-重置成功 false-重置失败
     */
    Boolean resetPasswordByEmailVerifyCode(ResetPasswordRequest resetPasswordRequest);
}
