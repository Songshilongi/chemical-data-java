package com.songshilong.starter.web.config;

import com.songshilong.starter.web.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.web.config
 * @Author: Shilong Song
 * @CreateTime: 2025-03-26  21:17
 * @Description: WebAutoConfiguration
 * @Version: 1.0
 */
@AutoConfiguration
public class WebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }


}
