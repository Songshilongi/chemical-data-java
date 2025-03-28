package com.songshilong.service.user.service;

import com.songshilong.service.user.dto.request.UserLoginRequest;
import com.songshilong.service.user.dto.request.UserRegisterRequest;
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
}
