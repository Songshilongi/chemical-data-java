package com.songshilong.service.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.dto.request
 * @Author: Ice, Song
 * @CreateTime: 2025-03-30  15:12
 * @Description: PasswordMailResetRequest
 * @Version: 1.0
 */
@Data
@ApiModel(description = "重置密码邮件请求参数")
public class PasswordMailResetRequest {

    @ApiModelProperty("唯一用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("邮箱地址")
    @Email(message = "邮箱地址格式不正确")
    private String email;


}
