package com.songshilong.service.task.config;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.songshilong.service.task.interceptor.TaskHeaderInterceptor;
import com.songshilong.service.task.properties.SnowFlakeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.config
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  17:22
 * @Description: TaskServiceConfiguration
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties(SnowFlakeProperties.class)
@RequiredArgsConstructor
public class TaskServiceConfiguration extends WebMvcConfigurationSupport {

    private final SnowFlakeProperties snowFlakeProperties;
    private final TaskHeaderInterceptor taskHeaderInterceptor;

    @Bean
    public SnowflakeGenerator snowflakeGenerator() {
        return new SnowflakeGenerator(snowFlakeProperties.getWorkerId(), snowFlakeProperties.getDataCenterId());
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(taskHeaderInterceptor)
                .addPathPatterns("/api/task-service/**");
        super.addInterceptors(registry);
    }
}
