package com.songshilong.service.gateway.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.gateway.filter
 * @Author: Ice, Song
 * @CreateTime: 2025-04-03  11:12
 * @Description: FilterConfig
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = FilterConfig.PREFIX)
public class FilterConfig {
    public static final String PREFIX = "chemical.property.filter.black-path";

    /**
     * 黑名单列表-需要被拦截的
     */
    private List<String> blackPathList;

    /**
     * 白名单，需要直接被放行
     */
    private List<String> whitePathList;

    /**
     * Jwt密钥
     */
    private String jwtSecret;

}
