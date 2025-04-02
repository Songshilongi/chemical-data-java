package com.songshilong.service.task.service.impl;

import cn.hutool.core.util.StrUtil;
import com.songshilong.module.starter.common.enums.TaskExceptionEnum;
import com.songshilong.module.starter.common.enums.TaskTypeEnum;
import com.songshilong.module.starter.common.exception.BusinessException;
import com.songshilong.service.task.dto.response.CreateTaskResponse;
import com.songshilong.service.task.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.service
 * @Author: Ice, Song
 * @CreateTime: 2025-03-31  16:34
 * @Description: TaskServiceImpl
 * @Version: 1.0
 */
@Service
public class TaskServiceImpl implements TaskService {


    @Override
    public CreateTaskResponse createTask(String type, String chemicalText, MultipartFile[] files) {
        if (StrUtil.isBlank(type)) {
            throw new BusinessException(TaskExceptionEnum.TASK_TYPE_NOT_DEFINED);
        }
        TaskTypeEnum code = TaskTypeEnum.getByCode(type);
        if (Objects.isNull(code)) {
            throw new BusinessException(TaskExceptionEnum.TASK_TYPE_NOT_DEFINED);
        }
        switch (code) {
            case TEXT:
                this.processText();
                break;
            case SMILES:
                break;
            case REACTION:
                break;
            case REAXYS:
                break;
            default:
                throw new BusinessException(TaskExceptionEnum.TASK_TYPE_NOT_EXIST);
        }


        return null;
    }

    /**
     * 处理创建文本任务
     */
    private void processText() {
    }
}
