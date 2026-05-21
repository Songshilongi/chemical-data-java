package com.songshilong.service.classify.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文管理类，用于在当前线程中存储和获取上下文信息
 */
public class BaseContext {

    private static final ThreadLocal<Map<String, Object>> CONTEXT = ThreadLocal.withInitial(HashMap::new);

    public static void setContext(String key, Object value) {
        CONTEXT.get().put(key, value);
    }

    public static Map<String, Object> getContext() {
        return CONTEXT.get();
    }

    /**
     * 获取上下文中指定键的值（原始 Object）
     */
    public static Object getContext(String key) {
        return CONTEXT.get().get(key);
    }

    /**
     * 获取 userId，统一返回 Long 类型
     * header 里传过来的是 String，需要在这里转换
     */
    public static Long getCurrentUserId() {
        Object val = CONTEXT.get().get(com.songshilong.module.starter.common.constant.Constant.USER_ID);
        // null 或空字符串都视为"未识别到用户"
        if (val == null || val.toString().isEmpty()) {
            throw new IllegalStateException("当前请求未识别到用户身份，请检查请求头 USER_ID");
        }
        if (val instanceof Long) {
            return (Long) val;
        }
        if (val instanceof Number) {
            return ((Number) val).longValue();
        }
        try {
            return Long.parseLong(val.toString());
        } catch (NumberFormatException e) {
            throw new IllegalStateException("USER_ID 格式非法: " + val);
        }
    }


    /**
     * 获取用户名（字符串类型）
     */
    public static String getCurrentUsername() {
        Object val = CONTEXT.get().get(com.songshilong.module.starter.common.constant.Constant.USERNAME);
        return val == null ? null : val.toString();
    }

    public static void removeContext(String key) {
        CONTEXT.get().remove(key);
    }

    public static void clearContext() {
        CONTEXT.remove();
    }
}
