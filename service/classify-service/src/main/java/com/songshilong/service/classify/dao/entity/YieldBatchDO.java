package com.songshilong.service.classify.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 产率预测任务任务数据库持久层实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_yield_batch")
public class YieldBatchDO {

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
     * 任务名称
     */
    private String taskName;

    /**
     * 预测结果下载地址
     */
    private String excelDownload;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
