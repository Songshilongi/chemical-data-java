package com.songshilong.service.task.service;

import com.songshilong.module.starter.common.result.Result;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
     *
     * @return 模版数据
     */
    ResponseEntity<Resource> getTextTemplate();

    /**
     * 上传分片文件
     *
     * @param file     分片文件数据
     * @param fileKey  分片文件对应的MD5 key
     * @param chunk    当前分片索引
     * @param total    总共的分片数量
     * @param filename 原始文件名
     * @return True-Success
     */
    Boolean uploadChunk(MultipartFile file, String fileKey, Integer chunk, Integer total, String filename);

    /**
     * 合并分片文件
     *
     * @param fileKey  文件唯一标识
     * @param total    总共的分片数量
     * @param filename 原始文件名
     * @return True-Success
     */
    Boolean mergeChunks(String fileKey, Integer total, String filename);

    /**
     * 检查文件是否已经存在（秒传）
     * @param fileKey 分片文件对应的Md5 value
     * @param filename 原始文件名
     * @return True-已经存在，无需上传
     */
    Boolean checkFile(String fileKey, String filename);
}
