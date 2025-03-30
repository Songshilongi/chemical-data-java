package com.songshilong.service.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.dto.request
 * @Author: Ice, Song
 * @CreateTime: 2025-03-30  20:58
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@ApiModel("邮箱验证码重置密码请求参数")
public class ResetPasswordRequest {
    @ApiModelProperty("唯一用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("邮箱地址")
    @Email(message = "邮箱地址格式不正确")
    private String email;

    @ApiModelProperty("新密码")
    @NotNull(message = "用户密码不能为空")
    @Size(max = 20, message = "用户密码长度不能超过20个字符")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "密码需包含数字、大小写字母，并且至少大于8位")
    private String newPassword;

    @ApiModelProperty("邮箱验证码")
    @NotNull(message = "邮箱验证码不能为空")
    private String verifyCode;
}
