package com.songshilong.service.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto
 * @Author: Ice, Song
 * @CreateTime: 2025-05-19  21:30
 * @Description: PartUploadCacheParam
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartUploadCacheParam {

    /**
     * 分片号
     */
    private Integer index;
    /**
     * 保存路径
     */
    private String path;

    /**
     * 总分片数量
     */
    private Integer total;
}
