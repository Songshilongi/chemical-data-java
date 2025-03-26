package com.songshilong.service.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.*;
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
@ApiModel(description = "用户注册请求参数")
public class UserRegisterRequest {

    @NotNull(message = "用户名不能为空")
    @Size(max = 20, message = "用户名长度不能超过20个字符")
    @ApiModelProperty("用户名/用户昵称")
    private String username;

    @NotNull(message = "用户密码不能为空")
    @Size(max = 20, message = "用户密码长度不能超过20个字符")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "密码需包含数字、大小写字母，并且至少大于8位")
    @ApiModelProperty("用户密码")
    private String password;


    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "用户邮箱格式不正确")
    @ApiModelProperty("用户邮箱")
    private String email;


    @ApiModelProperty("用户手机号")
    private String phone;

}
