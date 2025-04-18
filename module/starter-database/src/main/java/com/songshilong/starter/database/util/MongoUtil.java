package com.songshilong.starter.database.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.database.util
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  21:30
 * @Description: MongoUtil
 * @Version: 1.0
 */
@RequiredArgsConstructor
public class MongoUtil {

    private final MongoOperations mongoOperations;


    /**
     * 获取 MongoDb原始操作类
     */
    public MongoOperations getInstance() {
        return this.mongoOperations;
    }


    public <T> T findByDataId(String dataId, Class<T> clazz) {
        return mongoOperations.findById(dataId, clazz);
    }



}
