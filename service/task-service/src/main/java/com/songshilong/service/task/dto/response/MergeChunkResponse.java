package com.songshilong.service.task.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto.response
 * @Author: Ice, Song
 * @CreateTime: 2025-05-19  21:36
 * @Description: MergeChunkResponse
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("分片上传响应结果")
public class MergeChunkResponse {
    @ApiModelProperty("分片上传路径列表")
    private List<String> chunkPathList;
    @ApiModelProperty("缺失的分片索引列表")
    private List<Integer> missingChunkIndexList;
}
