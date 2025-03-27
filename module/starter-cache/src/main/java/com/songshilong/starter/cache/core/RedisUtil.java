package com.songshilong.starter.cache.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.cache.core
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  16:46
 * @Description: RedisUtil
 * @Version: 1.0
 */
@RequiredArgsConstructor
public class RedisUtil implements Cache{

    private final StringRedisTemplate redisTemplate;

    @Override
    public <T> T get(String key, Class<T> clazz) {
        redisTemplate.opsForValue().set(key, "value");
        return null;
    }
}
