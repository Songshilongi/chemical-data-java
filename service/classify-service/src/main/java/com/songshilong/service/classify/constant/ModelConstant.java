package com.songshilong.service.classify.constant;

/**
 * 模型相关常量
 *
 * 前后端约定：
 * - 系统模型只用 "10" / "50" 两种类型码（紧凑、稳定）
 * - 数据库 / 前端展示用 "系统默认10大类模型" 这种友好名
 * - 转换逻辑统一在本类
 */
public final class ModelConstant {

    /** 模型类型码：10大类 */
    public static final String MODEL_TYPE_10 = "10";

    /** 模型类型码：50类 */
    public static final String MODEL_TYPE_50 = "50";

    /** 展示名：10大类 */
    public static final String DISPLAY_NAME_10 = "系统默认10大类模型";

    /** 展示名：50类 */
    public static final String DISPLAY_NAME_50 = "系统默认50类模型";

    /**
     * 把模型类型码转成展示名。
     * 自定义模型不在此处理（传什么返回什么）。
     */
    public static String toDisplayName(String modelType) {
        if (MODEL_TYPE_10.equals(modelType)) return DISPLAY_NAME_10;
        if (MODEL_TYPE_50.equals(modelType)) return DISPLAY_NAME_50;
        return modelType;
    }

    private ModelConstant() {
        throw new UnsupportedOperationException("常量类不允许实例化");
    }
}
