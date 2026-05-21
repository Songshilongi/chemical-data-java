
package com.songshilong.service.classify.dto.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 分页查询接口请求参数实体
 */
@Data
@Schema(description = "反应分类分页查询参数")
public class YieldPageQueryRespDTO extends Page {
    /**
     * 任务id
     */
    @Schema(description = "任务id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 完整反应smiles
     */
    @Schema(description = "完整反应smiles")
    private String reactionSmiles;

    /**
     * 预测产率
     */
    @Schema(description = "预测产率")
    private String reactionYield;


    /**
     * 创建时间 / 预测完成时间
     */
    @Schema(description = "任务完成时间")
    private Date createTime;
}
