package com.songshilong.service.task.dto.request;

import com.songshilong.starter.database.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto.request
 * @Author: Ice, Song
 * @CreateTime: 2025-04-18  15:32
 * @Description: QueryHistoryTaskRequest - 查询历史记录请求参数
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("分页查询-历史抽取任务创建记录请求参数")
public class QueryHistoryTaskRequest extends BasePage {
    /**
     * 任务类型
     */
    @ApiModelProperty("任务类型")
    private String type;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;
}
