package com.songshilong.service.user.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.dto.response
 * @Author: Ice, Song
 * @CreateTime: 2025-03-30  15:12
 * @Description: PasswordMailResetResponse
 * @Version: 1.0
 */
@Data
@ApiModel(description = "重置密码邮件响应结果")
public class PasswordMailResetResponse {

    @ApiModelProperty("唯一用户名")
    private String username;

    @ApiModelProperty("邮箱地址")
    private String email;

    @ApiModelProperty("用于重置密码的验证码")
    private String validCode;

}
