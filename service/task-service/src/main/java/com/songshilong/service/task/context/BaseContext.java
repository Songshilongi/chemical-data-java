package com.songshilong.service.task.context;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.context
 * @Author: Ice, Song
 * @CreateTime: 2025-04-03  14:14
 * @Description: 上下文管理类，用于在当前线程中存储和获取上下文信息
 * @Version: 1.0
 */
public class BaseContext {

    private static final ThreadLocal<Map<String, Object>> CONTEXT = ThreadLocal.withInitial(HashMap::new);

    /**
     * 设置上下文中的键值对
     *
     * @param key   键
     * @param value 值
     */
    public static void setContext(String key, Object value) {
        CONTEXT.get().put(key, value);
    }

    /**
     * 获取上下文中的所有键值对
     *
     * @return 上下文中的键值对
     */
    public static Map<String, Object> getContext() {
        return CONTEXT.get();
    }

    /**
     * 获取上下文中指定键的值
     *
     * @param key 键
     * @return 指定键的值
     */
    public static Object getContext(String key) {
        return CONTEXT.get().get(key);
    }

    /**
     * 移除上下文中的指定键的值
     *
     * @param key 键
     */
    public static void removeContext(String key) {
        CONTEXT.get().remove(key);
    }

    /**
     * 清除当前线程的上下文
     */
    public static void clearContext() {
        CONTEXT.remove();
    }
}

