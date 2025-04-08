package com.songshilong.service.task.dao.entity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dao.entity
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  17:03
 * @Description: ReactionProcessResultEntity
 * @Version: 1.0
 */
@Data
@Document(collection = "reaction_process_result")
public class ReactionProcessResultEntity {

    @Id
    private String id;

    @Field("oss_url")
    private String ossUrl;


}
