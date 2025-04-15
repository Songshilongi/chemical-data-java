package com.songshilong.service.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dto
 * @Author: Ice, Song
 * @CreateTime: 2025-04-15  20:39
 * @Description: ReactionParam
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactionImageParam {

    /**
     * 底物-SMILES
     */
    private List<String> reactant;
    /**
     * 底物-名称
     */
    @Field("reactant_name")
    private List<String> reactantNameList;
    /**
     * 条件
     */
    private List<String> conditionList;

    /**
     * 条件-名称
     */
    @Field("condition_content")
    private List<String> conditionContentList;

    /**
     * 产物-SMILES
     */
    private List<String> product;
    /**
     * 产物-名称
     */
    @Field("product_name")
    private List<String> productNameList;



}
