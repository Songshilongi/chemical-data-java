package com.songshilong.module.starter.common.enums;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.enums
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  17:27
 * @Description: TODO
 * @Version: 1.0
 */
public enum TaskStatusEnum {
    CREATE_BUT_NOT_START(0, "任务已创建但未开始"),
    RUNNING(1, "当前任务正在处理中"),
    SUCCESS(2, "任务处理成功"),
    FAIL(3, "任务处理失败");

    private final Integer code;
    private final String desc;

    private TaskStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer code() {
        return this.code;
    }

    public String desc() {
        return this.desc;
    }


    public static TaskStatusEnum getByCode(Integer code) {
        for (TaskStatusEnum taskStatusEnum : TaskStatusEnum.values()) {
            if (taskStatusEnum.code().equals(code)) {
                return taskStatusEnum;
            }
        }
        return null;
    }
}
