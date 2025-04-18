package com.songshilong.module.starter.common.enums;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.enums
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  16:32
 * @Description: 任务类型枚举类
 * @Version: 1.0
 */
public enum TaskTypeEnum {

    TEXT("text", "化学有机合成文本描述信息抽取"),
    SMILES("smiles", "分子结构SMILES转换"),
    REACTION("reaction", "反应方程式结构化信息提取"),
    REAXYS("reaxys", "Reaxys化学文献ETE结构化信息抽取");


    private final String code;
    private final String desc;

    private TaskTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return this.code;
    }

    public String desc() {
        return this.desc;
    }

    public static TaskTypeEnum getByCode(String code) {
        for (TaskTypeEnum taskTypeEnum : TaskTypeEnum.values()) {
            if (taskTypeEnum.code.equals(code)) {
                return taskTypeEnum;
            }
        }
        return null;
    }

}
