package com.songshilong.module.starter.common.enums;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.enums
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  17:25
 * @Description: ClientExceptionEnum-客户端异常
 * @Version: 1.0
 */
public enum ClientExceptionEnum implements ExceptionHandler {
    JSON_SERIALIZE_FAIL(30001, "JSON序列化失败"),
    JSON_DESERIALIZE_FAIL(30002, "JSON反序列化失败"),
    REGISTER_GET_LOCK_FAIL(30003, "用户注册时尝试获取用户名锁失败"),
    TEMPLATE_RESOURCE_LOAD_FAIL(30004, "加载模板资源失败");


    private final Integer errorCode;
    private final String errorMsg;

    private ClientExceptionEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public Integer errorCode() {
        return this.errorCode;
    }

    @Override
    public String errorMsg() {
        return this.errorMsg;
    }
}
