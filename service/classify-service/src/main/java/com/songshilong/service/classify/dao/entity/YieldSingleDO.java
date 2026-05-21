package com.songshilong.service.classify.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 产率预测任务数据库持久层实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_yield_single")
public class YieldSingleDO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 反应smiles表达式
     */
    private String reactionSmiles;

    /**
     * 反应产率
     */
    private String reactionYield;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
