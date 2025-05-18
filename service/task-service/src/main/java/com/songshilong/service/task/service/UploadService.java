package com.songshilong.service.task.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.service
 * @Author: Ice, Song
 * @CreateTime: 2025-05-18  18:49
 * @Description: UploadService
 * @Version: 1.0
 */
public interface UploadService {


    /**
     * 获取文本批量处理的excel模版
     * @return 模版数据
     */
    ResponseEntity<Resource> getTextTemplate();
}
