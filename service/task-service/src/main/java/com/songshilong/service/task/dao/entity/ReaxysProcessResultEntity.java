package com.songshilong.service.task.dao.entity;

import com.songshilong.service.task.dto.ReaxysReactionParam;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.dao.entity
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  17:03
 * @Description: ReaxysProcessResultEntity
 * @Version: 1.0
 */
@Data
@Document(collection = "reaxys_process_result")
public class ReaxysProcessResultEntity {

    @Id
    private String id;

    /**
     * Reaxys PDF 阿里云OSS地址
     */
    @Field("oss_url")
    private String ossUrl;

    /**
     * Reaxys PDF处理的结构化数据结果
     */
    private List<ReaxysReactionParam> result;
}
