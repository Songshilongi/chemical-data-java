package com.songshilong.module.starter.common.exception;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.exception
 * @Author: Shilong Song
 * @CreateTime: 2025-03-26  21:46
 * @Description: BusinessException-业务异常
 * @Version: 1.0
 */
public class BusinessException extends AbstractException{
    /**
     * Constructor
     * @param message 异常信息
     * @param cause 异常原因
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     */
    public BusinessException(String message, Throwable cause, Integer errorCode, String errorMsg) {
        super(message, cause, errorCode, errorMsg);
    }
    public BusinessException(String message, Integer errorCode, String errorMsg) {
        this(message, null, errorCode, errorMsg);
    }
    public BusinessException(String message, Integer errorCode) {
        this(message, null, errorCode, null);
    }
    public BusinessException(Integer errorCode, String errorMsg) {
        this(null, null, errorCode, errorMsg);
    }

}
