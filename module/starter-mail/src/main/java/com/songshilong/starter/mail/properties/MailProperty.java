package com.songshilong.starter.mail.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.mail
 * @Author: Ice, Song
 * @CreateTime: 2025-03-30  14:16
 * @Description: MailProperty
 * @Version: 1.0
 */
@ConfigurationProperties(prefix = MailProperty.PREFIX)
public class MailProperty {
    public static final String PREFIX = "chemical.property.email";

    /**
     * 邮箱验证码有效时间
     */
    private String codeValidTime;
    /**
     * 有效时间单位
     */
    private TimeUnit timeUnit;
}
