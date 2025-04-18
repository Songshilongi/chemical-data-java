package com.songshilong.service.task.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.module.starter.common.enums.TaskExceptionEnum;
import com.songshilong.module.starter.common.enums.TaskStatusEnum;
import com.songshilong.module.starter.common.enums.TaskTypeEnum;
import com.songshilong.module.starter.common.exception.BusinessException;
import com.songshilong.service.task.context.BaseContext;
import com.songshilong.service.task.dao.entity.*;
import com.songshilong.service.task.dao.mapper.IETaskRecordMapper;
import com.songshilong.service.task.dto.request.QueryHistoryTaskRequest;
import com.songshilong.service.task.dto.response.QueryHistoryTaskResponse;
import com.songshilong.service.task.service.TaskService;
import com.songshilong.service.task.util.AliOssUtil;
import com.songshilong.starter.database.base.PageResult;
import com.songshilong.starter.database.util.MongoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final IETaskRecordMapper ieTaskRecordMapper;
    private final MongoUtil mongoUtil;
    private final AliOssUtil aliOssUtil;

    @Override
    public PageResult<QueryHistoryTaskResponse> queryHistoryTask(QueryHistoryTaskRequest queryHistoryTaskRequest) {
        IPage<IETaskRecordEntity> page = new Page<>(queryHistoryTaskRequest.getPageNumber(), queryHistoryTaskRequest.getPageSize());
        LambdaQueryWrapper<IETaskRecordEntity> wrapper = Wrappers.lambdaQuery(IETaskRecordEntity.class)
                .eq(IETaskRecordEntity::getUserId, BaseContext.getContext(Constant.USER_ID))
                .eq(!Objects.isNull(queryHistoryTaskRequest.getType()),IETaskRecordEntity::getTaskType, queryHistoryTaskRequest.getType())
                .ge(!Objects.isNull(queryHistoryTaskRequest.getStartTime()),IETaskRecordEntity::getCreateTime, queryHistoryTaskRequest.getStartTime())
                .le(!Objects.isNull(queryHistoryTaskRequest.getEndTime()),IETaskRecordEntity::getCreateTime, queryHistoryTaskRequest.getEndTime());
        IPage<IETaskRecordEntity> pageResult = ieTaskRecordMapper.selectPage(page, wrapper);
        List<IETaskRecordEntity> records = pageResult.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            return null;
        }
        List<QueryHistoryTaskResponse> responseList = records.stream().map(record -> {
            QueryHistoryTaskResponse response = new QueryHistoryTaskResponse();
            response.setTaskId(record.getId());
            response.setCreateTime(record.getCreateTime());
            response.setLatestUpdateTime(record.getUpdateTime());
            response.setTaskCode(record.getTaskType());
            Optional.ofNullable(TaskStatusEnum.getByCode(record.getStatus())).ifPresent(status -> response.setStatus(status.desc()));
            Optional.ofNullable(TaskTypeEnum.getByCode(record.getTaskType())).ifPresent(type -> response.setTaskType(type.desc()));
            return response;
        }).collect(Collectors.toList());

        return PageResult.<QueryHistoryTaskResponse>builder()
                .pageNumber(pageResult.getCurrent())
                .pages(pageResult.getPages())
                .pageSize(pageResult.getSize())
                .total(pageResult.getTotal())
                .result(responseList)
                .build();
    }

    @Override
    public void createTask(String type, String chemicalText, MultipartFile[] files) {
        if (StrUtil.isBlank(type)) {
            throw new BusinessException(TaskExceptionEnum.TASK_TYPE_NOT_DEFINED);
        }
        TaskTypeEnum code = TaskTypeEnum.getByCode(type);
        if (Objects.isNull(code)) {
            throw new BusinessException(TaskExceptionEnum.TASK_TYPE_NOT_DEFINED);
        }
        switch (code) {
            case TEXT:
                this.processText(chemicalText);
                break;
            case SMILES:
                this.processSmiles(files);
                break;
            case REACTION:
                this.processReaction(files);
                break;
            case REAXYS:
                this.processReaxys(files);
                break;
            default:
                throw new BusinessException(TaskExceptionEnum.TASK_TYPE_NOT_EXIST);
        }
    }

    /**
     * 处理创建Reaxys PDF 识别任务
     *
     * @param files 需要被识别的Reaxys PDF 文件
     */
    private void processReaxys(MultipartFile[] files) {
        if (ArrayUtil.isEmpty(files)) {
            return;
        }
        for (MultipartFile file : files) {
            Long dataId = Optional.ofNullable(snowflakeGenerator.next()).orElseThrow(() -> new RuntimeException("雪花算法生成错误"));
            IETaskRecordEntity ieTaskRecordEntity = IETaskRecordEntity.builder()
                    .userId(Long.valueOf(String.valueOf(BaseContext.getContext(Constant.USER_ID))))
                    .taskType(TaskTypeEnum.REAXYS.code())
                    .dataId(String.valueOf(dataId))
                    .status(TaskStatusEnum.CREATE_BUT_NOT_START.code())
                    .build();
            ReaxysProcessResultEntity reaxysProcessResultEntity = new ReaxysProcessResultEntity();
            reaxysProcessResultEntity.setId(String.valueOf(dataId));
            reaxysProcessResultEntity.setOssUrl(aliOssUtil.uploadFile(file, TaskTypeEnum.REAXYS));
            try {
                int insert = ieTaskRecordMapper.insert(ieTaskRecordEntity);
                mongoUtil.getInstance().insert(reaxysProcessResultEntity);
                if (insert != 1) {
                    throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
                }
            } catch (DuplicateKeyException exception) {
                throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
            }
        }
    }


    /**
     * 处理创建Reaction识别任务
     *
     * @param files 需要被识别的图片文件
     */
    private void processReaction(MultipartFile[] files) {
        if (ArrayUtil.isEmpty(files)) {
            return;
        }
        for (MultipartFile file : files) {
            Long dataId = Optional.ofNullable(snowflakeGenerator.next()).orElseThrow(() -> new RuntimeException("雪花算法生成错误"));
            IETaskRecordEntity ieTaskRecordEntity = IETaskRecordEntity.builder()
                    .userId(Long.valueOf(String.valueOf(BaseContext.getContext(Constant.USER_ID))))
                    .taskType(TaskTypeEnum.REACTION.code())
                    .dataId(String.valueOf(dataId))
                    .status(TaskStatusEnum.CREATE_BUT_NOT_START.code())
                    .build();
            ReactionProcessResultEntity reactionProcessResultEntity = new ReactionProcessResultEntity();
            reactionProcessResultEntity.setId(String.valueOf(dataId));
            reactionProcessResultEntity.setOssUrl(aliOssUtil.uploadFile(file, TaskTypeEnum.REACTION));
            try {
                int insert = ieTaskRecordMapper.insert(ieTaskRecordEntity);
                mongoUtil.getInstance().insert(reactionProcessResultEntity);
                if (insert != 1) {
                    throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
                }
            } catch (DuplicateKeyException exception) {
                throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
            }
        }
    }

    /**
     * 处理创建SMILES识别任务
     *
     * @param files 需要被识别的图片文件
     */
    private void processSmiles(MultipartFile[] files) {
        if (ArrayUtil.isEmpty(files)) {
            return;
        }
        for (MultipartFile file : files) {
            Long dataId = Optional.ofNullable(snowflakeGenerator.next()).orElseThrow(() -> new RuntimeException("雪花算法生成错误"));
            IETaskRecordEntity ieTaskRecordEntity = IETaskRecordEntity.builder()
                    .userId(Long.valueOf(String.valueOf(BaseContext.getContext(Constant.USER_ID))))
                    .taskType(TaskTypeEnum.SMILES.code())
                    .dataId(String.valueOf(dataId))
                    .status(TaskStatusEnum.CREATE_BUT_NOT_START.code())
                    .build();
            SmilesProcessResultEntity smilesProcessResultEntity = new SmilesProcessResultEntity();
            smilesProcessResultEntity.setId(String.valueOf(dataId));
            smilesProcessResultEntity.setOssUrl(aliOssUtil.uploadFile(file, TaskTypeEnum.SMILES));
            try {
                int insert = ieTaskRecordMapper.insert(ieTaskRecordEntity);
                mongoUtil.getInstance().insert(smilesProcessResultEntity);
                if (insert != 1) {
                    throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
                }
            } catch (DuplicateKeyException exception) {
                throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
            }

        }
    }

    /**
     * 处理创建文本任务
     *
     * @param chemicalText 化学合成文本
     */
    private void processText(String chemicalText) {
        if (StrUtil.isBlank(chemicalText)) {
            return;
        }
        Long dataId = Optional.ofNullable(snowflakeGenerator.next()).orElseThrow(() -> new RuntimeException("雪花算法生成错误"));
        IETaskRecordEntity ieTaskRecordEntity = IETaskRecordEntity.builder()
                .userId(Long.valueOf(String.valueOf(BaseContext.getContext(Constant.USER_ID))))
                .taskType(TaskTypeEnum.TEXT.code())
                .dataId(String.valueOf(dataId))
                .status(TaskStatusEnum.CREATE_BUT_NOT_START.code())
                .build();
        TextProcessResultEntity entity = new TextProcessResultEntity();
        entity.setId(String.valueOf(dataId));
        entity.setChemicalText(chemicalText);
        try {
            int insert = ieTaskRecordMapper.insert(ieTaskRecordEntity);
            mongoUtil.getInstance().insert(entity);
            if (insert != 1) {
                throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
            }
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(TaskExceptionEnum.TASK_CREATE_FAIL);
        }
    }
}
