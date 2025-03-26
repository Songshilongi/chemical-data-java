package com.songshilong.service.user.controller;

import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.user.dto.request.UserRegisterRequest;
import com.songshilong.service.user.dto.response.UserRegisterResponse;
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
public class UserRegisterController {


    @PostMapping("/register")
    public Result<UserRegisterResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return Result.success(null);
    }


}
