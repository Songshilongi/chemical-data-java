package com.songshilong.service.task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task
 * @Author: Ice, Song
 * @CreateTime: 2025-03-31  16:21
 * @Description: TaskServiceApplication
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("com.songshilong.service.task.dao.mapper")
public class TaskServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class);
    }
}
