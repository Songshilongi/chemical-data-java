package com.songshilong.service.user.util;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.util
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  19:41
 * @Description: TODO
 * @Version: 1.0
 */
public class UserUtil {

    private static final int SHARDING_COUNT = 128;

    /**
     * 计算用户名redis集合的分片位置
     * @param username 用户名
     * @return 粉片位置
     */
    public static int hashShardingIndex(String username) {
        return Math.abs(username.hashCode() % SHARDING_COUNT);
    }
}
