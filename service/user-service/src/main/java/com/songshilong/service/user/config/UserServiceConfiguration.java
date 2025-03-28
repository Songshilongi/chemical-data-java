package com.songshilong.service.user.config;

import com.songshilong.service.user.properties.UserJwtProperty;
import com.songshilong.service.user.properties.UsernameBloomFilterProperty;
import org.redisson.RedissonBloomFilter;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.config
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  16:57
 * @Description: UserServiceConfiguration
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties({UserJwtProperty.class, UsernameBloomFilterProperty.class})
public class UserServiceConfiguration {


    @Bean
    public RBloomFilter<String> usernameBloomFilter(RedissonClient redissonClient, UsernameBloomFilterProperty usernameBloomFilterProperty) {
        RBloomFilter<String> usernameBloomFilter = redissonClient.getBloomFilter(usernameBloomFilterProperty.getName());
        usernameBloomFilter.tryInit(usernameBloomFilterProperty.getExpectedInterceptors(), usernameBloomFilterProperty.getFalseProbability());
        return usernameBloomFilter;
    }

}
