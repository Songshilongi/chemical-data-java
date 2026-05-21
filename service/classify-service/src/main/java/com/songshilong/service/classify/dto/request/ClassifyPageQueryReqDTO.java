package com.songshilong.service.classify.dto.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询接口请求参数实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "反应分类分页查询参数")
public class ClassifyPageQueryReqDTO extends Page {

    @Schema(description = "完整反应smiles")
    @Size(max = 5000, message = "查询关键字过长")
    private String reactionSmiles;
}
