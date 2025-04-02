package com.songshilong.service.task.config;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.config
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  17:22
 * @Description: TaskServiceConfiguration
 * @Version: 1.0
 */
@Configuration
public class TaskServiceConfiguration {

    @Bean
    public SnowflakeGenerator snowflakeGenerator() {
        return new SnowflakeGenerator(1, 0);
    }
}
