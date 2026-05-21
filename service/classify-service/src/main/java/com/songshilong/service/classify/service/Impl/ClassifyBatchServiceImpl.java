package com.songshilong.service.classify.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songshilong.module.starter.common.exception.BusinessException;
import com.songshilong.service.classify.constant.ModelConstant;
import com.songshilong.service.classify.context.BaseContext;
import com.songshilong.service.classify.dao.entity.ClassifyBatchDO;
import com.songshilong.service.classify.dao.mapper.ClassifyBatchMapper;
import com.songshilong.service.classify.dto.request.ClassifyBatchReqDTO;
import com.songshilong.service.classify.dto.request.ClassifyPageBatchQueryReqDTO;
import com.songshilong.service.classify.dto.response.ClassifyPageBatchQueryRespDTO;
import com.songshilong.service.classify.enums.ClassifyExceptionEnum;
import com.songshilong.service.classify.service.ClassifyBatchService;
import com.songshilong.service.classify.util.ClassifyUpload;
import com.songshilong.service.classify.util.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassifyBatchServiceImpl extends ServiceImpl<ClassifyBatchMapper, ClassifyBatchDO> implements ClassifyBatchService {

    private static final Logger logger = LoggerFactory.getLogger(ClassifyBatchServiceImpl.class);

    private final ClassifyBatchMapper classifyBatchMapper;
    private final ClassifyUpload classifyUpload;
    private final RocketMQTemplate rocketMQTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchClassify(ClassifyBatchReqDTO requestParam) {
        Long userId = BaseContext.getCurrentUserId();
        String BATCH_CLASSIFY_TOPIC = "reaction-batch-classify-topic";
        // 1. 生成唯一的任务ID (JobId)，这是串联整个流程的关键
        String jobId = UUID.randomUUID().toString();
        // 2. 先创建任务实体并存入数据库，设置初始状态为 "SUBMITTED"
        ClassifyBatchDO classifyBatchDO = BeanUtil.toBean(requestParam, ClassifyBatchDO.class);
        classifyBatchDO.setJobId(jobId);
        classifyBatchDO.setUserId(userId);
        classifyBatchDO.setStatus("SUBMITTED"); // 初始状态：已提交
        // TODO: EXCEL传给后端批量预测
        String modelURL = "";
        String modelName;
        String excelUploadURL;
        
        // 验证excel文件是否上传
        FileValidator.validateExcelFile(requestParam.getExcelFile());
        try {
            Map<String, String> excelUpload = classifyUpload.uploadFile(requestParam.getExcelFile());
            excelUploadURL = excelUpload.get("fileUrl");
        } catch (IOException e) {
            log.error("上传 Excel 到 OSS 失败", e);
            throw new BusinessException(ClassifyExceptionEnum.FILE_UPLOAD_FAIL);
        }
        
        // 校验并获取模型
        // 自行上传
        if(requestParam.getCustomModel() == 0){
            FileValidator.validateModelFile(requestParam.getModelFile());
            try {
                // 上传模型到阿里云，返回模型URL传给python
                String fileName = requestParam.getModelFile().getOriginalFilename(); // 获取完整文件名
                if (fileName != null && fileName.contains(".")) {
                    modelName = fileName.substring(0, fileName.lastIndexOf(".")); // 去掉后缀
                } else {
                    modelName = fileName; // 没有后缀，直接赋值
                }
                Map<String, String> resultMap = classifyUpload.uploadFile(requestParam.getModelFile());
                modelURL = resultMap.get("fileUrl");
            } catch (IOException e) {
                log.error("上传自定义模型到 OSS 失败", e);
                throw new BusinessException(ClassifyExceptionEnum.FILE_UPLOAD_FAIL);
            }
        // 使用默认模型    
        } else if (requestParam.getCustomModel() == 1) {
            modelName = ModelConstant.toDisplayName(requestParam.getPredictModel());
        } else {
            throw new BusinessException(ClassifyExceptionEnum.INVALID_MODEL_TYPE);
        }
        classifyBatchDO.setPredictModel(modelName);
        // 3. 将任务信息存入数据库
        // 这一步必须在发送MQ之前，确保任务可追踪
        classifyBatchMapper.insert(classifyBatchDO);
        log.info("批量分类任务已创建, JobId: {}", jobId);

        // 定义消息体
        JSONObject messageBody = new JSONObject();
        messageBody.put("jobId", jobId); // 必须包含jobId
        messageBody.put("customModel", requestParam.getCustomModel());
        messageBody.put("excelUnpreditedURL", excelUploadURL);
        // 根据上传类型发送不同的消息
        if (requestParam.getCustomModel() == 0) {
            messageBody.put("modelUrl", modelURL);
        } else {
            messageBody.put("predictModel", requestParam.getPredictModel());
        }

        // 执行 RocketMQ5.x 消息队列发送&异常处理逻辑
        try {
            rocketMQTemplate.convertAndSend(BATCH_CLASSIFY_TOPIC, messageBody);
            log.info("成功发送批量分类任务到MQ, JobId: {}", jobId);
        } catch (Exception ex) {
            log.error("发送批量分类任务到MQ失败, JobId: {}. 正在回滚任务状态.", jobId, ex);
            // 如果发送失败，更新数据库中的任务状态为失败
            classifyBatchDO.setStatus("FAILED");
            classifyBatchDO.setRemark("任务提交失败，无法发送到处理队列");
            this.updateById(classifyBatchDO);
            // 向上抛出异常，Controller可以捕获并告知用户提交失败
            throw new BusinessException(ClassifyExceptionEnum.MQ_SEND_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<ClassifyPageBatchQueryRespDTO> pageBatchQueryClassify(ClassifyPageBatchQueryReqDTO requestParam) {
        // 构建分页查询模板 LambdaQueryWrapper
        LambdaQueryWrapper<ClassifyBatchDO> queryWrapper = Wrappers.lambdaQuery(ClassifyBatchDO.class)
                .eq(ClassifyBatchDO::getUserId, BaseContext.getCurrentUserId())
                .like(StrUtil.isNotBlank(requestParam.getTaskName()), ClassifyBatchDO::getTaskName, requestParam.getTaskName());

        // MyBatis-Plus 分页查询
        IPage<ClassifyBatchDO> selectPage = classifyBatchMapper.selectPage(requestParam, queryWrapper);
        // 转换数据库持久层对象返回参数
        return selectPage.convert(each -> BeanUtil.toBean(each, ClassifyPageBatchQueryRespDTO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteClassifyTask(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        Long userId = BaseContext.getCurrentUserId();

        LambdaUpdateWrapper<ClassifyBatchDO> updateWrapper = Wrappers.lambdaUpdate(ClassifyBatchDO.class)
                .in(ClassifyBatchDO::getBatchId, ids)
                .eq(ClassifyBatchDO::getUserId, userId)
                .eq(ClassifyBatchDO::getDeleted, 0)
                .set(ClassifyBatchDO::getDeleted, 1)
                .set(ClassifyBatchDO::getUpdateTime, LocalDateTime.now());

        int deletedCount = classifyBatchMapper.update(null, updateWrapper);

        log.info("用户 {} 请求删除 {} 条批量任务，实际删除 {} 条", userId, ids.size(), deletedCount);

        if (deletedCount < ids.size()) {
            log.warn("用户 {} 试图删除不属于自己的批量任务！请求 ids={}, 实际删除={}", userId, ids, deletedCount);
        }

        return deletedCount > 0;
    }
}
