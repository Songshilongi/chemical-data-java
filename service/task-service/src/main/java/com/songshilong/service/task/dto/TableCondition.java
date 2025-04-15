package com.songshilong.service.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto
 * @Author: Ice, Song
 * @CreateTime: 2025-04-15  20:59
 * @Description: TableCondition
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableCondition {

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
}
