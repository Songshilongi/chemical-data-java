package com.songshilong.starter.cache.config;

import cn.hutool.json.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.songshilong.starter.cache.core.RedisUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.cache.config
 * @Author: Shilong Song
 * @CreateTime: 2025-03-27  16:13
 * @Description: CacheAutoConfiguration
 * @Version: 1.0
 */
@AutoConfiguration
public class CacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisUtil redisUtil(StringRedisTemplate stringRedisTemplate) {
        return new RedisUtil(stringRedisTemplate);
    }
}
