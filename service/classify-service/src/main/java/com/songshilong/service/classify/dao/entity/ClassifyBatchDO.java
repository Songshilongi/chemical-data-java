package com.songshilong.service.classify.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 反应分类任务任务数据库持久层实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_classify_batch")
public class ClassifyBatchDO {

    /**
     * 批次任务id
     */
    @TableId(value = "batch_id", type = IdType.AUTO)
    private Long batchId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 任务名称，取Excel名
     */
    private String taskName;

    /**
     * 使用的预测模型名称
     */
    private String predictModel;

    /**
     * 是否为用户上传模型（0: 用户上传，1: 使用系统模型）
     */
    private Integer customModel;

    /**
     * 预测结果下载地址
     */
    private String excelDownload;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 跨服务追踪的唯一任务ID (推荐使用)
     * 在任务创建时由Java服务生成 (e.g., UUID.randomUUID().toString())
     * 这个ID会传递给Python，并在Python返回结果时带回，用于关联任务。
     */
    private String jobId;

    /**
     * 用户上传的原始Excel文件在OSS上的地址或Key
     */
    private String excelUpload;

    /**
     * 用户上传的自定义模型文件在OSS上的地址或Key
     */
    private String modelUpload;

    /**
     * 任务状态 (例如: SUBMITTED, PROCESSING, SUCCESS, FAILED)
     */
    private String status;

    /**
     * 备注信息，可用于记录任务失败的原因
     */
    private String remark;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted = 0;

}



