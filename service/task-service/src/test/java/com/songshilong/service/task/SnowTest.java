package com.songshilong.service.task;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  22:17
 * @Description: SnowTest
 * @Version: 1.0
 */
@SpringBootTest
public class SnowTest {

    @Autowired
    private SnowflakeGenerator snowflakeGenerator;

    @Test
    public void test() {
        System.out.println(snowflakeGenerator.next());
        System.out.println(snowflakeGenerator.next());
        System.out.println(snowflakeGenerator.next());
        System.out.println(snowflakeGenerator.next());
    }


}
