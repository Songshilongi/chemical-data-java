package com.songshilong.service.task.dao.entity;

import com.songshilong.service.task.dto.ReactionImageParam;
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
 * @Description: ReactionProcessResultEntity
 * @Version: 1.0
 */
@Data
@Document(collection = "reaction_process_result")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactionProcessResultEntity {

    @Id
    private String id;

    /**
     * 反应方程式图
     */
    @Field("oss_url")
    private String ossUrl;

    /**
     * 反应方程式图对应的反应组成
     */
    private List<ReactionImageParam> result;
}
