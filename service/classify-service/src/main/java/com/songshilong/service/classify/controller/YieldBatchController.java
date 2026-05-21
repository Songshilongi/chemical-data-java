package com.songshilong.service.classify.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.songshilong.module.starter.common.idempotent.NoDuplicateSubmit;
import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.classify.dto.request.YieldBatchReqDTO;
import com.songshilong.service.classify.dto.request.YieldPageBatchQueryReqDTO;
import com.songshilong.service.classify.dto.response.YieldPageBatchQueryRespDTO;
import com.songshilong.service.classify.service.YieldBatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "批量产率预测")
public class YieldBatchController {
    private final YieldBatchService yieldBatchService;
    @Operation(summary = "提交批量产率预测任务")
    @NoDuplicateSubmit(message = "请勿短时间内重复提交产率预测任务")
    @PostMapping(value = "/api/yield-task/predict/create-batch",consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Boolean> batchReactionYield(
            @RequestParam("taskName") String taskName,
            @RequestPart("excelFile") MultipartFile excelFile
    ) {
        YieldBatchReqDTO requestParam = new YieldBatchReqDTO();
        requestParam.setTaskName(taskName);
        requestParam.setExcelFile(excelFile);
        yieldBatchService.batchYield(requestParam);
        return Result.success(Boolean.TRUE);
    }

    @Operation(summary = "分页查询批量产率预测历史记录")
    @GetMapping("/api/yield-task/predict/query-page-batch")
    public Result<IPage<YieldPageBatchQueryRespDTO>> pageBatchQueryYield(YieldPageBatchQueryReqDTO requestParam) {
        return Result.success(yieldBatchService.pageBatchQueryYield(requestParam));
    }

    @Operation(summary = "删除产率预测任务")
    @DeleteMapping("/api/yield-task/predict/delete-batch")
    public Result<Boolean> deleteBatchYieldTask(@RequestBody List<Long> ids) {
        // 调用service层删除
        boolean isDeleted = yieldBatchService.deleteYieldTask(ids);
        if (isDeleted) {
            return Result.success(Boolean.TRUE);
        } else {
            return Result.fail(500,"删除失败",  Boolean.FALSE);
        }
    }
}
