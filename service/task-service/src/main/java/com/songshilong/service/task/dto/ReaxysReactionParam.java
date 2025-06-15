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
 * @CreateTime: 2025-04-15  20:45
 * @Description: ReaxysParam
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReaxysReactionParam {

    @Field("reaxys_id")
    private String reaxysId;

    /**
     * 底物-SMILES
     */
    private List<String> reactant;
    /**
     * 底物-OSS地址
     */
    private List<String> reactantOssList;

    /**
     * 底物-名称
     */
    @Field("reactant_name")
    private List<String> reactantNameList;

    /**
     * 产物-SMILES
     */
    private List<String> product;

    /**
     * 产物-OSS地址
     */
    private List<String> productOssList;
    /**
     * 产物-名称
     */
    private List<String> productNameList;

    /**
     * 表格反应条件列表
     */
    @Field("table_condition_list")
    private List<TableComponent> tableComponentList;


}
