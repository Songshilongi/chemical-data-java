package com.songshilong.service.task.service;

import com.songshilong.service.task.dto.request.QueryHistoryTaskRequest;
import com.songshilong.service.task.dto.response.QueryHistoryTaskResponse;
import com.songshilong.starter.database.base.PageResult;
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
    void createTask(String type, String chemicalText, MultipartFile[] files);

    /**
     * 查询某个用户的历史抽取任务创建记录
     * @param queryHistoryTaskRequest 查询参数 {@link QueryHistoryTaskRequest}
     * @return 分页查询历史记录 {@link QueryHistoryTaskResponse}
     */
    PageResult<QueryHistoryTaskResponse> queryHistoryTask(QueryHistoryTaskRequest queryHistoryTaskRequest);

    /**
     * 查询具体抽取任务的处理结果详情
     * @param taskId IE任务ID
     * @return 处理结果详情
     */
    Object queryDetail(Long taskId);

}
