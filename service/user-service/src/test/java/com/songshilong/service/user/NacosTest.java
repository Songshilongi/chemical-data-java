package com.songshilong.service.user;

import com.songshilong.service.user.properties.UserJwtProperty;
import com.songshilong.service.user.properties.UsernameBloomFilterProperty;
import com.songshilong.module.starter.common.utils.BeanUtil;
import com.songshilong.module.starter.common.utils.Md5SecurityUtil;
import com.songshilong.service.user.dto.request.UserRegisterRequest;
import com.songshilong.service.user.dto.response.UserRegisterResponse;
import com.songshilong.starter.cache.core.RedisUtil;
import com.songshilong.starter.mail.EmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${test.name}")
    private String name;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserJwtProperty userJwtProperty;

    @Autowired
    private UsernameBloomFilterProperty usernameBloomFilterProperty;

    @Autowired
    private EmailUtil emailUtil;


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
        System.out.println(usernameBloomFilterProperty);
    }

    @Test
    public void testSendEmail() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head><title></title></head><body>");
        stringBuilder.append("您好<br/>");
        stringBuilder.append("您的验证码是：").append("verifyCode").append("<br/>");
        stringBuilder.append("您可以复制此验证码并返回至科研管理系统找回密码页面，以验证您的邮箱。<br/>");
        stringBuilder.append("此验证码只能使用一次，在5");
        stringBuilder.append("分钟内有效。验证成功则自动失效。<br/>");
        stringBuilder.append("如果您没有进行上述操作，请忽略此邮件。");
        String content = stringBuilder.toString();
        System.out.println(emailUtil.send("lsabeltaylorsee@163.com", "Test", content));

    }


}
