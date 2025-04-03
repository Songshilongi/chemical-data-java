package com.songshilong.service.task.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto.response
 * @Author: Ice, Song
 * @CreateTime: 2025-03-31  16:39
 * @Description: CreateTaskResponse
 * @Version: 1.0
 */
@Data
@ApiModel("创建任务需要返回的结果")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskResponse {


    @ApiModelProperty("任务类型")
    private String taskType;

    @ApiModelProperty("任务创建成功返回的任务ID")
    private Long taskId;




}
