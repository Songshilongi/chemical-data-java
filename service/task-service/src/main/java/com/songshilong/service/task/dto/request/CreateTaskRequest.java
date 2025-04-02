package com.songshilong.service.task.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto.request
 * @Author: Ice, Song
 * @CreateTime: 2025-03-31  16:39
 * @Description: CreateTaskRequest
 * @Version: 1.0
 */
@Data
@ApiModel("创建抽取任务请求参数")
public class CreateTaskRequest {

    @ApiModelProperty("文本参数抽取-化学反应文本")
    private String chemicalText;






}
