package com.songshilong.service.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.properties
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  16:06
 * @Description: UserJwtProperty
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = UserJwtProperty.USER_JWT_PREFIX)
public class UserJwtProperty {
    public static final String USER_JWT_PREFIX = "chemical.property.jwt.user";

    /**
     * 密钥
     */
    private String secretKey;
    /**
     * 过期时间
     */
    private Long ttlMillis;


}
