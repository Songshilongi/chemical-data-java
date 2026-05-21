package com.songshilong.service.classify.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.songshilong.module.starter.common.idempotent.NoDuplicateSubmit;
import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.classify.dto.request.ClassifyBatchReqDTO;
import com.songshilong.service.classify.dto.request.ClassifyPageBatchQueryReqDTO;
import com.songshilong.service.classify.dto.response.ClassifyPageBatchQueryRespDTO;
import com.songshilong.service.classify.service.ClassifyBatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated   // ← 类上加这个
@Tag(name = "批量反应分类")
public class ClassifyBatchController {
    private final ClassifyBatchService classifyBatchService;

    @Operation(summary = "提交批量反应分类任务")
    @NoDuplicateSubmit(message = "请勿短时间内重复提交反应类型预测任务")
    @PostMapping(value = "/api/classify-task/predict/create-batch",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Boolean> batchReactionClassify(
            @RequestParam("taskName")
            @NotBlank(message = "任务名称不能为空")
            @Size(max = 100, message = "任务名称长度不能超过100")
            String taskName,

            @RequestParam("predictModel")
            @Size(max = 200, message = "模型名称长度不能超过200")
            String predictModel,

            @RequestPart(value = "modelFile", required = false) MultipartFile modelFile,

            @RequestParam("customModel")
            @NotNull(message = "customModel 不能为空")
            Integer customModel,

            @RequestPart(value = "excelFile", required = false) MultipartFile excelFile
    ) {
        ClassifyBatchReqDTO requestParam = new ClassifyBatchReqDTO();
        requestParam.setTaskName(taskName);
        requestParam.setPredictModel(predictModel);
        requestParam.setModelFile(modelFile);
        requestParam.setCustomModel(customModel);
        requestParam.setExcelFile(excelFile);
        classifyBatchService.batchClassify(requestParam);
        return Result.success(Boolean.TRUE);
    }

    @Operation(summary = "分页查询批量反应分类历史记录")
    @GetMapping("/api/classify-task/predict/query-page-batch")
    public Result<IPage<ClassifyPageBatchQueryRespDTO>> pageBatchQueryClassify(
            @Valid ClassifyPageBatchQueryReqDTO requestParam) {
        return Result.success(classifyBatchService.pageBatchQueryClassify(requestParam));
    }

    @Operation(summary = "删除反应分类任务")
    @DeleteMapping("/api/classify-task/predict/delete-batch")
    public Result<Boolean> deleteBatchClassifyTask(@RequestBody List<Long> ids) {
        boolean isDeleted = classifyBatchService.deleteClassifyTask(ids);
        if (isDeleted) {
            return Result.success(Boolean.TRUE);
        } else {
            return Result.fail(500,"删除失败",  Boolean.FALSE);
        }
    }
}

