package com.songshilong.module.starter.common.exception;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.exception
 * @Author: Shilong Song
 * @CreateTime: 2025-03-26  21:30
 * @Description: AbstractException
 * @Version: 1.0
 */
public class AbstractException extends RuntimeException{

    protected final Integer errorCode;
    protected final String errorMsg;

    public AbstractException(String message, Throwable cause, Integer errorCode, String errorMsg) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
