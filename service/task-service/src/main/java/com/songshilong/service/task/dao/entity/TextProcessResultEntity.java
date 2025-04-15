package com.songshilong.service.task.dao.entity;

import com.songshilong.service.task.dto.StandardReaction;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dao.entity
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  17:01
 * @Description: 文本抽取结果MongoDB
 * @Version: 1.0
 */
@Data
@Document(collection = "text_process_result")
public class TextProcessResultEntity {

    /**
     * id
     */
    @Id
    private String id;
    /**
     * 化学文本原文
     */
    @Field("chemical_text")
    private String chemicalText;
    /**
     * 抽取结果
     */
    private List<StandardReaction> result;
}
