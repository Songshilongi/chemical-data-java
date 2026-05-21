package com.songshilong.service.classify.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "批量反应分类分页查询参数")
public class YieldPageBatchQueryRespDTO {
    /**
     * 任务id
     */
    @Schema(description = "任务批次id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long batchId;

    /**
     * 完整反应smiles
     */
    @Schema(description = "任务名称")
    private String taskName;

    /**
     * 下载链接
     */
    @Schema(description = "下载链接")
    private String excelDownload;

    /**
     * 创建时间 / 预测完成时间
     */
    @Schema(description = "任务完成时间")
    private Date createTime;
}
