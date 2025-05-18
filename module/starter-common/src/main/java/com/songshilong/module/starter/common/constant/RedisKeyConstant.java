package com.songshilong.module.starter.common.constant;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.constant
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  14:42
 * @Description: RedisKeyConstant
 * @Version: 1.0
 */
public class RedisKeyConstant {
    public static final String PROJECT_PREFIX = "chemical-data-java:";
    public static final String USER_REGISTER_USERNAME_LOCK_PREFIX = "user:register:username:lock:";
    public static final String USER_REGISTER_USERNAME = "user:register:username:";

    /**
     * 用户邮箱找回密码的Redis Key前缀
     */
    public static final String USER_RESET_PASSWORD_EMAIL_PREFIX = "user:email-reset-password:verify:username:";
    /**
     * 用户邮箱找回密码时限制一定时间内只能存在一次
     */
    public static final String USER_RESET_PASSWORD_FLAG_PREFIX = "user:email-reset-password:flag:username:";


    /**
     * 分片上传用于存储已经上传过的分片文件查询前缀
     */
    public static final String CHUNK_UPLOAD_PREFIX = "chunk-upload:uploaded:";


}
