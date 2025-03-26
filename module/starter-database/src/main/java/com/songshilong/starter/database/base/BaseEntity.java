package com.songshilong.starter.database.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.database.base
 * @Author: Shilong Song
 * @CreateTime: 2025-03-26  14:23
 * @Description: BaseEntity-每个数据库实体都必须要有的字段
 * @Version: 1.0
 */
@Data
public class BaseEntity {
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 删除标志
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
