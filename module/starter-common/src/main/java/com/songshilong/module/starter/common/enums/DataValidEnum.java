package com.songshilong.module.starter.common.enums;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.enums
 * @Author: Shilong Song
 * @CreateTime: 2025-03-26  14:41
 * @Description: DataValidEnum
 * @Version: 1.0
 */
public enum DataValidEnum {
    /**
     * 数据有效-未删除
     */
    VALID(0),
    /**
     * 数据无效-已删除
     */
    INVALID(1);



    private final Integer code;
    private DataValidEnum(Integer code) {
        this.code = code;
    }

    public Integer code() {
        return this.code;
    }


}
