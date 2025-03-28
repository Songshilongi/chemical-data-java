package com.songshilong.service.user.config;

import com.songshilong.module.starter.common.properties.UserJwtProperty;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.config
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  16:57
 * @Description: UserServiceConfiguration
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties({UserJwtProperty.class})
public class UserServiceConfiguration {



}
