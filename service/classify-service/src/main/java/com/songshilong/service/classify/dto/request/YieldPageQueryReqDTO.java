
package com.songshilong.service.classify.dto.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页查询接口请求参数实体
 */
@Data
@Schema(description = "反应分类分页查询参数")
public class YieldPageQueryReqDTO extends Page {
    /**
     * smiles
     */
    @Schema(description = "完整反应smiles")
    private String reactionSmiles;

}
