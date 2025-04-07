package com.songshilong.service.gateway;

import com.songshilong.service.gateway.filter.FilterConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.gateway
 * @Author: Ice, Song
 * @CreateTime: 2025-04-07  16:22
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootTest
public class NacosTest {

    @Autowired
    private FilterConfig filterConfig;


    @Test
    public void Test() {
        System.out.println(filterConfig);

    }



}
