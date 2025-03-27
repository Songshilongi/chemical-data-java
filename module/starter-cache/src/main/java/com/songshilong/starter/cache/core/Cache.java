package com.songshilong.starter.cache.core;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.cache.core
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  16:28
 * @Description: Cache
 * @Version: 1.0
 */
public interface Cache {
    /**
     * 获取缓存
     * @param key redis key
     * @param clazz 缓存对象类型
     * @return T
     */
    <T> T get( String key, Class<T> clazz);
}
