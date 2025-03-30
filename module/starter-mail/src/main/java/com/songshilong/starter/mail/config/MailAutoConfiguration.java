package com.songshilong.starter.mail.config;

import com.songshilong.starter.mail.EmailUtil;
import com.songshilong.starter.mail.properties.MailProperty;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.mail.config
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  23:21
 * @Description: MailAutoConfiguration
 * @Version: 1.0
 */
@AutoConfiguration
@EnableConfigurationProperties(MailProperty.class)
public class MailAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EmailUtil emailService(JavaMailSender javaMailSender) {
        return new EmailUtil(javaMailSender);
    }
}
