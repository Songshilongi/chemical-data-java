package com.songshilong.service.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user
 * @Author: Shilong Song
 * @CreateTime: 2025-03-24  20:52
 * @Description: UserServiceApplication
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("com.songshilong.service.user.dao.mapper")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class);
    }
}
