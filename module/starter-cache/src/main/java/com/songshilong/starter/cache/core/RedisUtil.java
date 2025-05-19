package com.songshilong.starter.cache.core;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.songshilong.module.starter.common.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
public class RedisUtil implements Cache {

    private final StringRedisTemplate redisTemplate;

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

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


    @Override
    public void setAdd(String key, String... value) {
        redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public Boolean setIsMember(String key, Object obj) {
        return redisTemplate.opsForSet().isMember(key, obj);
    }


    /**
     * 获取所有
     *
     * @param namespace 命名空间
     * @return key下所有的键
     */
    public List<String> getAllFromNameSpace(String namespace) {
        if (StrUtil.isBlank(namespace)) {
            return Collections.emptyList();
        }
        Set<String> keys = redisTemplate.keys(namespace + "*");
        if (CollectionUtil.isEmpty(keys)) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        keys.forEach(key -> result.add(this.get(key)));
        return result;
    }
}
