package com.songshilong.starter.mq.config;

import com.songshilong.starter.mq.core.RocketMqUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.mq.config
 * @Author: Ice, Song
 * @CreateTime: 2025-04-15  21:23
 * @Description: RocketMqConfiguration
 * @Version: 1.0
 */
@AutoConfiguration
public class RocketMqConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RocketMqUtil rocketMqUtil(RocketMQTemplate rocketMQTemplate) {
        return new RocketMqUtil(rocketMQTemplate);
    }

}
