package com.songshilong.service.task.service;

import com.songshilong.service.task.dto.response.CreateTaskResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.service.impl
 * @Author: Ice, Song
 * @CreateTime: 2025-03-31  16:33
 * @Description: TODO
 * @Version: 1.0
 */
public interface TaskService {
    /**
     * 创建抽取任务
     * @param type text/smiles/reaction/reaxys
     * @param chemicalText - 文本抽取的时候需要上传的有机化合合成反应文本
     * @param files - smiles/reaction/reaxys 对应 图片/图片/文件
     */
    CreateTaskResponse createTask(String type, String chemicalText, MultipartFile[] files);


}
