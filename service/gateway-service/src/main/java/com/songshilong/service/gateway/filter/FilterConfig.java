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
public class FilterConfig {

    /**
     * 黑名单列表-需要被拦截的
     */
    private List<String> blackPathPre;

    /**
     * 白名单，需要直接被放行
     */
    private List<String> whitePathPre;

    /**
     * Jwt密钥
     */
    private String jwtSecret;

}
