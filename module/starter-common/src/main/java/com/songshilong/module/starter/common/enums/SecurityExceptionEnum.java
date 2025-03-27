package com.songshilong.module.starter.common.enums;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.enums
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  15:15
 * @Description: SecurityExceptionEnum
 * @Version: 1.0
 */
public enum SecurityExceptionEnum implements ExceptionHandler{
    FAIL_GET_MD5_ORIGIN(20001, "MD5原文加密失败");


    private final Integer errorCode;
    private final String errorMsg;

    private SecurityExceptionEnum(Integer errorCode, String errorMsg)
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
