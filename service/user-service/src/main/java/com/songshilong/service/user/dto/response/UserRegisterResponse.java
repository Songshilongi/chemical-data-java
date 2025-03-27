package com.songshilong.service.user.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.songshilong.service.user.serialize.PhoneDesensitizationSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.dto.response
 * @Author: Ice, Song
 * @CreateTime: 2025-03-26  15:32
 * @Description: UserRegisterResponse
 * @Version: 1.0
 */
@Data
@ApiModel("User Register Response")
public class UserRegisterResponse {

    @ApiModelProperty("用户名/用户昵称")
    private String username;

    @ApiModelProperty("用户邮箱")
    private String email;

    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    @ApiModelProperty("用户手机号")
    private String phone;

}
