package com.songshilong.module.starter.common.enums;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.enums
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  14:49
 * @Description: UserExceptionEnum
 * @Version: 1.0
 */
public enum UserExceptionEnum {
    USER_NOT_EXIST(10001, "用户不存在"),
    USER_EXIST(10002, "用户已存在"),
    USER_REGISTER_FAIL(10003, "用户注册失败"),
    USER_LOGIN_FAIL(10004, "用户登录失败"),
    USER_LOGOUT_FAIL(10005, "用户登出失败"),
    USER_LOGIN_EXPIRED(10006, "用户登录过期");

    private final Integer errorCode;
    private final String errorMsg;

    private UserExceptionEnum(Integer errorCode, String errorMsg)
    {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer errorCode() {
        return this.errorCode;
    }
    public String errorMsg() {
        return this.errorMsg;
    }
}
