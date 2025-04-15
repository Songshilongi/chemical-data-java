package com.songshilong.service.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto
 * @Author: Ice, Song
 * @CreateTime: 2025-04-15  20:56
 * @Description: TableCondition
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableComponent {

    /**
     * 产量/产率
     */
    @Field("yield_rate")

    private String yieldRate;
    /**
     * 表格中每一个格子的文本代表的反应条件
     */
    @Field("condition_list")
    private List<TableCondition> tableConditionList;
}
