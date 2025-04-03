package com.songshilong.service.gateway.config;

import com.songshilong.service.gateway.filter.FilterConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.gateway.config
 * @Author: Ice, Song
 * @CreateTime: 2025-04-03  11:22
 * @Description: GatewayServiceConfiguration
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties(FilterConfig.class)
public class GatewayServiceConfiguration {



}
