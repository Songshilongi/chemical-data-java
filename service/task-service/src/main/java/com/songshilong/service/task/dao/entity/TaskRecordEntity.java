package com.songshilong.service.task.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.songshilong.starter.database.base.BaseEntity;
import lombok.Data;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dao.entity
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  16:30
 * @Description: TaskRecordEntity
 * @Version: 1.0
 */
@Data
@TableName("task_record")
public class TaskRecordEntity extends BaseEntity {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long userID;
    /**
     * 任务类型 {@link com.songshilong.module.starter.common.enums.TaskTypeEnum}
     */
    private String taskType;

    /**
     * 抽取结果存放在MongoDB
     */
    private String dataId;

    /**
     * 当前任务执行的状态
     */
    private Integer status;


}
