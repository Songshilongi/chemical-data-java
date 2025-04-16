package com.songshilong.starter.mq.enums;

import lombok.Getter;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.mq.enums
 * @Author: Ice, Song
 * @CreateTime: 2025-04-16  20:12
 * @Description: RocketMQTopicEnum
 * @Version: 1.0
 */
@Getter
public enum RocketMQCenterEnum {
    IE_TEXT("多模态信息抽取", RocketMQCenterEnum.TASK_SERVICE_IE, "text", "文本模态反应参数结构化提取"),
    IE_SMILES("多模态信息抽取", RocketMQCenterEnum.TASK_SERVICE_IE, "smiles", "分子结构图片识别"),
    IE_REACTION("多模态信息抽取", RocketMQCenterEnum.TASK_SERVICE_IE, "reaction", "反应方程式识别"),
    IE_REAXYS("多模态信息抽取", RocketMQCenterEnum.TASK_SERVICE_IE, "reaxys", "ReaxysPDF文献端到端结构化反应数据提取");

    private final String category;
    private final String topic;
    private final String tag;
    private final String desc;
    private static final String TASK_SERVICE_IE = "task-service_IE";

    private RocketMQCenterEnum(String category, String topic, String tag, String desc) {
        this.category = category;
        this.topic = topic;
        this.tag = tag;
        this.desc = desc;
    }

}
