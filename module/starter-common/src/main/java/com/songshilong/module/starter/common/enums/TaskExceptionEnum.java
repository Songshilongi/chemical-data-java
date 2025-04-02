package com.songshilong.module.starter.common.enums;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.enums
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  17:32
 * @Description: TODO
 * @Version: 1.0
 */
public enum TaskExceptionEnum implements ExceptionHandler{
    TASK_TYPE_NOT_DEFINED(60001, "任务类型未定义"),
    TASK_TYPE_NOT_EXIST(60002, "任务类型不存在");


    private final Integer errorCode;
    private final String errorMsg;

    private TaskExceptionEnum(Integer errorCode, String errorMsg) {
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
