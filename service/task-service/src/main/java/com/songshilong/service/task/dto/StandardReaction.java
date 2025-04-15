package com.songshilong.service.task.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  17:12
 * @Description: ReactionParam - 核心反应参数
 * @Version: 1.0
 */
@Data
public class StandardReaction implements Serializable {
    private static final long serialVersionUID = 328479823748932L;

    /**
     * 底物
     */
    private List<String> reactant;
    /**
     * 产物
     */
    private List<String> product;
    /**
     * 溶剂
     */
    private List<String> solvent;
    /**
     * 催化剂
     */
    private List<String> catalyst;
    /**
     * 一般试剂
     */
    private List<String> reagent;
    /**
     * 后处理试剂
     */
    private List<String> after;
    /**
     * 反应温度
     */
    private List<String> temperature;
    /**
     * 反应时间
     */
    private List<String> time;
    /**
     * 产量/产率
     */
    @Field("yield_rate")
    private List<String> yieldRate;
}
