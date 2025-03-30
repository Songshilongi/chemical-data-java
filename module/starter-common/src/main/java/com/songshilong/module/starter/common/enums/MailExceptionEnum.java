package com.songshilong.module.starter.common.enums;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.enums
 * @Author: Ice, Song
 * @CreateTime: 2025-03-30  14:11
 * @Description: TODO
 * @Version: 1.0
 */
public enum MailExceptionEnum implements ExceptionHandler {
    UNKNOWN_TARGET(40001, "需要发送的内容或者目标邮件地址为空，请检查！"),
    SEND_FAIL(40002, "邮件发送失败"),
    TOO_FREQUENCY(40003, "邮件发送过于频繁");


    private final Integer errorCode;
    private final String errorMsg;

    private MailExceptionEnum(Integer errorCode, String errorMsg) {
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
