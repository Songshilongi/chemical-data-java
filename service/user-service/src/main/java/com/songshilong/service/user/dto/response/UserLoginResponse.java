package com.songshilong.service.user.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.songshilong.service.user.serialize.PhoneDesensitizationSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.dto.response
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  14:21
 * @Description: UserLoginResponse
 * @Version: 1.0
 */
@Data
@ApiModel("用户登录响应")
public class UserLoginResponse {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名/用户昵称")
    private String username;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户手机号")
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;

    @ApiModelProperty("token")
    private String token;

}
