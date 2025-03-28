package com.songshilong.service.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.dto.request
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  14:21
 * @Description: UserLoginRequest
 * @Version: 1.0
 */
@Data
@ApiModel("用户登录请求参数")
public class UserLoginRequest {


    @ApiModelProperty("登录类型: 1.用户名密码匹配方式登录 2. 手机号验证吗登录 3. 邮箱验证码登录")
    @NotNull
    private Integer loginType;

    @ApiModelProperty("用户名/用户昵称")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("用户邮箱")
    private String email;
    @ApiModelProperty("用户手机号")
    private String phone;



}
