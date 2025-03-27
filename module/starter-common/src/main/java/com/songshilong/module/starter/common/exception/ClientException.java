package com.songshilong.module.starter.common.exception;

import com.songshilong.module.starter.common.enums.UserExceptionEnum;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.exception
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  14:28
 * @Description: ClientException
 * @Version: 1.0
 */
public class ClientException extends AbstractException {

    /**
     * Constructor
     *
     * @param message   异常信息
     * @param cause     异常原因
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    public ClientException(String message, Throwable cause, Integer errorCode, String errorMsg) {
        super(message, cause, errorCode, errorMsg);
    }

    public ClientException(String message, Integer errorCode, String errorMsg) {
        this(message, null, errorCode, errorMsg);
    }

    public ClientException(String message, Integer errorCode) {
        this(message, null, errorCode, null);
    }

    public ClientException(Integer errorCode, String errorMsg) {
        this(null, null, errorCode, errorMsg);
    }

    public ClientException(UserExceptionEnum userExceptionEnum) {
        this(userExceptionEnum.errorCode(), userExceptionEnum.errorMsg());
    }

}
