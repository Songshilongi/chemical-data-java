package com.songshilong.service.classify.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "批量反应分类分页查询参数")
public class ClassifyPageBatchQueryRespDTO {
    /**
     * 任务id
     */
    @Schema(description = "任务批次id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long batchId;

    /**
     * 完整反应smiles
     */
    @Schema(description = "任务名称（excel文件名）")
    private String taskName;

    /**
     * 反应类型
     */
    @Schema(description = "预测模型名称")
    private String predictModel;

    /**
     * 下载链接
     */
    @Schema(description = "下载链接")
    private String excelDownload;

    /**
     * 创建时间 / 预测完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "任务完成时间")
    private LocalDateTime createTime;

    /**
     * 当前状态
     */
    @Schema(description = "当前状态")
    private String status;

    @Schema(description = "备注/失败原因")
    private String remark;
}
