package com.songshilong.starter.cache.core;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.cache.core
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  16:28
 * @Description: Cache
 * @Version: 1.0
 */
public interface Cache {

    String get(String key);

    /**
     * 获取缓存对象 - String
     *
     * @param key   redis key
     * @param clazz 缓存对象类型
     * @return T
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 设置缓存 - String
     *
     * @param key   key
     * @param value value
     */
    void set(String key, String value);

    /**
     * 设置缓存 - String
     *
     * @param key      key
     * @param value    value
     * @param timeout  过期时间
     * @param timeUnit 过期时间的单位
     */
    void set(String key, String value, long timeout, TimeUnit timeUnit);


    /**
     * 向Set中添加元素
     *
     * @param key   key
     * @param value value
     */
    void setAdd(String key, String... value);

    /**
     * 判断Set中是否存在元素
     *
     * @param key key
     * @param obj 被判断的元素
     */
    Boolean setIsMember(String key, Object obj);


}
