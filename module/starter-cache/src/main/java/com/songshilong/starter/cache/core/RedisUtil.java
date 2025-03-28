package com.songshilong.starter.cache.core;

import com.songshilong.module.starter.common.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

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
        String value = redisTemplate.opsForValue().get(key);
        return BeanUtil.toObject(value, clazz);
    }


    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }
}
