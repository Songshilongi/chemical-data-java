package com.songshilong.service.user;

import com.songshilong.module.starter.common.properties.UserJwtProperty;
import com.songshilong.module.starter.common.utils.BeanUtil;
import com.songshilong.module.starter.common.utils.Md5SecurityUtil;
import com.songshilong.service.user.dto.request.UserRegisterRequest;
import com.songshilong.service.user.dto.response.UserRegisterResponse;
import com.songshilong.starter.cache.core.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

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

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserJwtProperty userJwtProperty;


    @Test
    public void test(){
        System.out.println(name);
    }

    @Test
    public void testBean() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("songshilong");
        userRegisterRequest.setPassword("123456");
        userRegisterRequest.setEmail("songshilong@163.com");
        userRegisterRequest.setPhone("12345678901");
        UserRegisterResponse result = BeanUtil.convert(userRegisterRequest, UserRegisterResponse.class);
        System.out.println(result);

    }

    @Test
    public void testMd5() {
        String password = "20010503sslS";
        System.out.println(Md5SecurityUtil.getMd5Value(password));
        System.out.println(Md5SecurityUtil.getMd5ValueWithSalt(password));
    }


    @Test
    public void RedisTest() {
        redisUtil.get("test", String.class);
    }

    @Test
    public void TestJSON() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("songshilong");
        userRegisterRequest.setPassword("123456");
        userRegisterRequest.setEmail("songshilong@163.com");
        userRegisterRequest.setPhone("12345678901");
        String json = BeanUtil.toJSON(userRegisterRequest);
        System.out.println(json);
        UserRegisterRequest result = BeanUtil.toObject(json, UserRegisterRequest.class);
        System.out.println(result);
    }

    @Test
    public void testProperty() {
        System.out.println(userJwtProperty);
    }


}
