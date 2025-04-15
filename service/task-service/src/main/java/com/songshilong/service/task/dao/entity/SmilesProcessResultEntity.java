package com.songshilong.service.task.dao.entity;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dao.entity
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  17:03
 * @Description: SmilesProcessResultEntity
 * @Version: 1.0
 */
@Data
@Document(collection = "smiles_process_result")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmilesProcessResultEntity {

    @Id
    private String id;

    /**
     * 分子结构图阿里云OSS下载地址
     */
    @Field("oss_url")
    private String ossUrl;

    /**
     * 分子结构图对应的SMILES表达式
     */
    private String smiles;



}
