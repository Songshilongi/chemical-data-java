package com.songshilong.service.classify;

import com.songshilong.starter.web.config.WebAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task
 * @Author: Ice, Song
 * @CreateTime: 2025-03-31  16:21
 * @Description: TaskServiceApplication
 * @Version: 1.0
 */
@SpringBootApplication(exclude = {WebAutoConfiguration.class})
@MapperScan("com.songshilong.service.classify.dao.mapper")
@EnableScheduling
public class ClassifyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClassifyServiceApplication.class);
    }
}

