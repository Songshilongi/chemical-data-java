package com.songshilong.service.task.service.impl;

import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.StrUtil;
import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.module.starter.common.enums.TaskExceptionEnum;
import com.songshilong.module.starter.common.enums.TaskStatusEnum;
import com.songshilong.module.starter.common.enums.TaskTypeEnum;
import com.songshilong.module.starter.common.exception.BusinessException;
import com.songshilong.module.starter.common.exception.ClientException;
import com.songshilong.service.task.context.BaseContext;
import com.songshilong.service.task.dao.entity.TaskRecordEntity;
import com.songshilong.service.task.dao.entity.TextProcessResultEntity;
import com.songshilong.service.task.dao.mapper.TaskRecordMapper;
import com.songshilong.service.task.dto.response.CreateTaskResponse;
import com.songshilong.service.task.service.TaskService;
import com.songshilong.starter.database.util.MongoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Struct;
import java.util.Objects;
import java.util.Optional;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.service
 * @Author: Ice, Song
 * @CreateTime: 2025-03-31  16:34
 * @Description: TaskServiceImpl
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final SnowflakeGenerator snowflakeGenerator;
    private final TaskRecordMapper taskRecordMapper;
    private final MongoUtil mongoUtil;


    @Override
    public CreateTaskResponse createTask(String type, String chemicalText, MultipartFile[] files) {
        if (StrUtil.isBlank(type)) {
            throw new BusinessException(TaskExceptionEnum.TASK_TYPE_NOT_DEFINED);
        }
        TaskTypeEnum code = TaskTypeEnum.getByCode(type);
        if (Objects.isNull(code)) {
            throw new BusinessException(TaskExceptionEnum.TASK_TYPE_NOT_DEFINED);
        }
        Long taskId = null;
        switch (code) {
            case TEXT:
                taskId = this.processText(chemicalText);
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
        if (Objects.isNull(taskId)) {
            throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
        }
        return CreateTaskResponse
                .builder()
                .taskType(type)
                .taskId(taskId)
                .build();
    }

    /**
     * 处理创建文本任务
     * @param chemicalText 化学合成文本
     */
    private Long processText(String chemicalText) {
        if (StrUtil.isBlank(chemicalText)) {
            return null;
        }
        Long dataId = Optional.ofNullable(snowflakeGenerator.next()).orElseThrow(() -> new RuntimeException("雪花算法生成错误"));
        TaskRecordEntity taskRecordEntity = TaskRecordEntity
                .builder()
                .userId(Long.valueOf(String.valueOf(BaseContext.getContext(Constant.USER_ID))))
                .taskType(TaskTypeEnum.TEXT.code())
                .dataId(String.valueOf(dataId))
                .status(TaskStatusEnum.CREATE_BUT_NOT_START.code())
                .build();
        TextProcessResultEntity entity = new TextProcessResultEntity();
        entity.setId(String.valueOf(dataId));
        entity.setChemicalText(chemicalText);
        try {
            int insert = taskRecordMapper.insert(taskRecordEntity);
            mongoUtil.getInstance().insert(entity);
            if (insert != 1) {
                throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
            }
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
        }
        return dataId;
    }
}
