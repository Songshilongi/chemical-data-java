package com.songshilong.service.user.controller;

import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.user.dto.request.PasswordMailResetRequest;
import com.songshilong.service.user.dto.request.UserLoginRequest;
import com.songshilong.service.user.dto.request.UserRegisterRequest;
import com.songshilong.service.user.dto.response.PasswordMailResetResponse;
import com.songshilong.service.user.dto.response.UserLoginResponse;
import com.songshilong.service.user.dto.response.UserRegisterResponse;
import com.songshilong.service.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.controller
 * @Author: Ice, Song
 * @CreateTime: 2025-03-26  15:16
 * @Description: UserRegisterController
 * @Version: 1.0
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "用户相关接口")
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Result<UserRegisterResponse> register(@RequestBody @Validated UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse userRegisterResponse = userService.register(userRegisterRequest);
        return Result.success(userRegisterResponse);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result<UserLoginResponse> login(@RequestBody @Validated UserLoginRequest userLoginRequest) {
        UserLoginResponse userLoginResponse = userService.login(userLoginRequest);
        return Result.success(userLoginResponse);
    }


    @PostMapping("/reset/mail-code")
    @ApiOperation(value = "获取密码重置邮箱验证码")
    public Result<PasswordMailResetResponse> getPasswordMailResetCode(@RequestBody PasswordMailResetRequest passwordMailResetRequest){
        PasswordMailResetResponse response = userService.getPasswordMailResetCode(passwordMailResetRequest);
        return Result.success(response);
    }

    @PostMapping("/reset")
    @ApiOperation(value = "重置密码")
    public void reset() {

    }
}
