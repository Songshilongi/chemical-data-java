package com.songshilong.service.compound;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.compound
 * @Author: Ice, Song
 * @CreateTime: 2025-05-08  21:52
 * @Description: NacosTest
 * @Version: 1.0
 */
@SpringBootTest
public class NacosTest {
    @Value("${hello}")
    private String hello;

    @Test
    public void test() {
        System.out.println(hello);
    }

}
