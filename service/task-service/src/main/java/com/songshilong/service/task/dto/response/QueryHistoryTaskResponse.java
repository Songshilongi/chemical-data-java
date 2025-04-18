package com.songshilong.service.task.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.print.attribute.standard.PrinterURI;
import java.time.LocalDateTime;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto.response
 * @Author: Ice, Song
 * @CreateTime: 2025-04-18  15:37
 * @Description: 历史记录查询结果
 * @Version: 1.0
 */
@Data
@ApiModel("分页查询-抽取任务历史创建记录")
public class QueryHistoryTaskResponse {

    @ApiModelProperty("任务ID")
    private Long taskId;

    @ApiModelProperty("任务类型Code")
    private String taskCode;
    @ApiModelProperty("任务类型")
    private String taskType;

    @ApiModelProperty("任务状态")
    private String status;

    @ApiModelProperty("任务创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最近一次更新时间")
    private LocalDateTime latestUpdateTime;


}
