package com.songshilong.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user
 * @Author: Shilong Song
 * @CreateTime: 2025-03-24  21:12
 * @Description: NacosTest
 * @Version: 1.0
 */
@SpringBootTest
public class NacosTest {

    @Value("${user.name}")
    private String name;
    
    
    @Test
    public void test(){
        System.out.println(name);
    }

}
