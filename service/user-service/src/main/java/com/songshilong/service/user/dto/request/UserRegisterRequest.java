package com.songshilong.service.user.dto.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.dto.request
 * @Author: Ice, Song
 * @CreateTime: 2025-03-26  15:31
 * @Description: UserRegisterRequest
 * @Version: 1.0
 */
@Data
public class UserRegisterRequest {


    /**
     * 用户名/用户昵称
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String phone;

}
