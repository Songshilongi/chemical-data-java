package com.songshilong.service.task;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.service.task.context.BaseContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Test
    public void cal() {
        String value = "upload.xlsx|dwgegrgegthgnhyjscdsfesfdfweafeae";
        System.out.println((value.hashCode() & 0x7FFFFFFF) & 15);
        String ossKey = String.format("chunks/%s/%s.part", "songshilong", "key");
        System.out.println(ossKey);
    }


}
