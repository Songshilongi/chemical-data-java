package com.songshilong.service.classify.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songshilong.module.starter.common.exception.BusinessException;
import com.songshilong.service.classify.constant.ModelConstant;
import com.songshilong.service.classify.context.BaseContext;
import com.songshilong.service.classify.dao.entity.ClassifySingleDO;
import com.songshilong.service.classify.dao.mapper.ClassifyMapper;
import com.songshilong.service.classify.dto.request.ClassifyPageQueryReqDTO;
import com.songshilong.service.classify.dto.request.ClassifySingleReqDTO;
import com.songshilong.service.classify.dto.response.ClassifyPageQueryRespDTO;
import com.songshilong.service.classify.enums.ClassifyExceptionEnum;
import com.songshilong.service.classify.service.ClassifyService;
import com.songshilong.service.classify.util.ClassifyUpload;
import com.songshilong.service.classify.util.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapper, ClassifySingleDO> implements ClassifyService {

    private final ClassifyUpload classifyUpload;
    private final ClassifyMapper classifyMapper;
    private final RestTemplate restTemplate;

    @Value("${python-service.url}")
    private String pythonServiceUrl;
    // 处理单个上传
    @Override
    public void classifySingle(ClassifySingleReqDTO requestParam) {
        // 1. 提前获取并校验用户身份
        Long userId = BaseContext.getCurrentUserId();

        // 2. 构造发给 Python 的请求体
        JSONObject pythonRequestPayload = new JSONObject();
        pythonRequestPayload.put("inputType", requestParam.getInputType());
        pythonRequestPayload.put("customModel", requestParam.getCustomModel());

        // 3. 根据输入方式填充不同字段
        if ("smiles".equals(requestParam.getInputType())) {
            pythonRequestPayload.put("reactionSmiles", requestParam.getReactionSmiles());
        } else if ("split".equals(requestParam.getInputType())) {
            pythonRequestPayload.put("reactants", requestParam.getReactants());
            pythonRequestPayload.put("conditions", requestParam.getConditions());
            pythonRequestPayload.put("products", requestParam.getProducts());
        }

        // 4. 处理模型：自定义模型先上传到 OSS
        if (requestParam.getCustomModel() == 0) {
            FileValidator.validateModelFile(requestParam.getModelFile());
            try {
                Map<String, String> resultMap = classifyUpload.uploadFile(requestParam.getModelFile());
                pythonRequestPayload.put("modelUrl", resultMap.get("fileUrl"));
            } catch (IOException e) {
                log.error("上传自定义模型到 OSS 失败", e);
                throw new BusinessException(ClassifyExceptionEnum.FILE_UPLOAD_FAIL);
            }
        } else {
            // 系统模型
            pythonRequestPayload.put("predictModel", requestParam.getPredictModel());
        }

        // 5. 调用 Python 服务
        log.info("调用Python进行单个预测, payload: {}", pythonRequestPayload.toJSONString());
        String predictApiUrl = pythonServiceUrl + "/api/classify/single";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(pythonRequestPayload.toJSONString(), headers);

        String jsonResponse;
        try {
            jsonResponse = restTemplate.postForObject(predictApiUrl, entity, String.class);
        } catch (HttpClientErrorException e) {
            log.warn("Python服务返回客户端错误: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            String pythonErrorMsg = extractPythonErrorMessage(e.getResponseBodyAsString());
            throw new BusinessException(
                    ClassifyExceptionEnum.PYTHON_PREDICT_FAIL.errorCode(),
                    "预测失败: " + pythonErrorMsg
            );
        } catch (Exception e) {
            log.error("调用Python预测服务异常", e);
            throw new BusinessException(ClassifyExceptionEnum.PYTHON_SERVICE_UNAVAILABLE);
        }

        JSONObject result = JSONObject.parseObject(jsonResponse);
        String reactionClass = result.getString("reactionClass");
        String stitchedSmiles = result.getString("stitchedSmiles");

        if (reactionClass == null || stitchedSmiles == null) {
            log.error("Python服务返回结果格式错误: {}", jsonResponse);
            throw new BusinessException(ClassifyExceptionEnum.PYTHON_RESPONSE_INVALID);
        }

        // 6. 存库
        ClassifySingleDO classifySingleDO = new ClassifySingleDO();
        classifySingleDO.setUserId(userId);
        classifySingleDO.setReactionSmiles(stitchedSmiles);
        classifySingleDO.setReactionClass(reactionClass);
        classifySingleDO.setPredictModel(getPredictModelName(requestParam));

        classifyMapper.insert(classifySingleDO);
        log.info("单个预测成功, 用户: {}, 反应类别: {}, 存储ID: {}",
                userId, reactionClass, classifySingleDO.getId());
    }

    /**
     * 从 Python FastAPI 的错误响应里提取 detail 字段，作为友好提示
     */
    private String extractPythonErrorMessage(String responseBody) {
        if (responseBody == null || responseBody.isEmpty()) {
            return "未知错误";
        }
        try {
            JSONObject json = JSONObject.parseObject(responseBody);
            String detail = json.getString("detail");
            return detail != null ? detail : responseBody;
        } catch (Exception e) {
            return responseBody;
        }
    }


    private String getPredictModelName(ClassifySingleReqDTO requestParam) {
        if (requestParam.getCustomModel() == 0) {
            String originalFilename = requestParam.getModelFile().getOriginalFilename();
            if (originalFilename != null && originalFilename.contains(".")) {
                return originalFilename.substring(0, originalFilename.lastIndexOf("."));
            }
            return originalFilename;
        } else {
            return ModelConstant.toDisplayName(requestParam.getPredictModel());
        }
    }

    // 分页查询
    @Override
    public IPage<ClassifyPageQueryRespDTO> pageQueryClassify(ClassifyPageQueryReqDTO requestParam) {
        // 构建分页查询模板 LambdaQueryWrapper
        LambdaQueryWrapper<ClassifySingleDO> queryWrapper = Wrappers.lambdaQuery(ClassifySingleDO.class)
                .eq(ClassifySingleDO::getUserId, BaseContext.getCurrentUserId())
                .like(StrUtil.isNotBlank(requestParam.getReactionSmiles()), ClassifySingleDO::getReactionSmiles, requestParam.getReactionSmiles());

        // MyBatis-Plus 分页查询
        IPage<ClassifySingleDO> selectPage = classifyMapper.selectPage(requestParam, queryWrapper);

        // 转换数据库持久层对象返回参数
        return selectPage.convert(each -> BeanUtil.toBean(each, ClassifyPageQueryRespDTO.class));
    }


    // 根据id批量删除
    @Override
    public boolean deleteClassifyTask(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        Long userId = BaseContext.getCurrentUserId();

        // 软删除：手动 set deleted=1 + updateTime（替代 delete(wrapper)，确保 update_time 被刷新）
        LambdaUpdateWrapper<ClassifySingleDO> updateWrapper = Wrappers.lambdaUpdate(ClassifySingleDO.class)
                .in(ClassifySingleDO::getId, ids)
                .eq(ClassifySingleDO::getUserId, userId)
                .eq(ClassifySingleDO::getDeleted, 0)              // 防止重复删除
                .set(ClassifySingleDO::getDeleted, 1)
                .set(ClassifySingleDO::getUpdateTime, LocalDateTime.now());

        int deletedCount = classifyMapper.update(null, updateWrapper);

        log.info("用户 {} 请求删除 {} 条记录，实际删除 {} 条", userId, ids.size(), deletedCount);

        if (deletedCount < ids.size()) {
            log.warn("用户 {} 试图删除不属于自己的记录！请求 ids={}, 实际删除={}", userId, ids, deletedCount);
        }

        return deletedCount > 0;
    }


}
