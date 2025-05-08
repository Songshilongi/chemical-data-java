package com.songshilong.service.compound;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.compound
 * @Author: Ice, Song
 * @CreateTime: 2025-05-08  21:17
 * @Description: CompoundServiceApplication
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("com.songshilong.service.compound.dao.mapper")
public class CompoundServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompoundServiceApplication.class);
    }
}
