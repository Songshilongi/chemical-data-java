package com.songshilong.service.classify.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.songshilong.module.starter.common.idempotent.NoDuplicateSubmit;
import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.classify.dto.request.*;
import com.songshilong.service.classify.dto.response.YieldPageQueryRespDTO;
import com.songshilong.service.classify.service.YieldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "产率预测")
public class YieldController {
    private final YieldService yieldService;

    @NoDuplicateSubmit(message = "请勿短时间内重复提交产率预测任务")
    @PostMapping(value="/api/yield-task/predict/create-single",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "提交产率预测任务")
    public Result<Boolean> reactionYield(
            @RequestParam("inputType") String inputType,
            @RequestParam(value = "reactionSmiles", required = false) String reactionSmiles,
            @RequestPart(value = "reactants", required = false) List<MoleculeInputDTO> reactants,
            @RequestPart(value = "conditions", required = false) List<MoleculeInputDTO> conditions,
            @RequestPart(value = "products", required = false) List<MoleculeInputDTO> products
    ) {
        YieldSingleReqDTO requestParam = new YieldSingleReqDTO();
        requestParam.setInputType(inputType);
        requestParam.setReactionSmiles(reactionSmiles);
        requestParam.setReactants(reactants);
        requestParam.setConditions(conditions);
        requestParam.setProducts(products);
        yieldService.yieldSingle(requestParam);
        return Result.success(Boolean.TRUE);
    }

    @Operation(summary = "分页查询产率预测历史记录")
    @GetMapping("/api/yield-task/predict/query-page")
    public Result<IPage<YieldPageQueryRespDTO>> pageQueryYield(YieldPageQueryReqDTO requestParam) {
        return Result.success(yieldService.pageQueryYield(requestParam));
    }

    @Operation(summary = "删除产率预测任务")
    @DeleteMapping("/api/yield-task/predict/delete")
    public Result<Boolean> deleteYieldTask(@RequestBody List<Long> ids) {
        // 调用service层删除
        boolean isDeleted = yieldService.deleteYieldTask(ids);
        if (isDeleted) {
            return Result.success(Boolean.TRUE);
        } else {
            return Result.fail(500,"删除失败",  Boolean.FALSE);
        }
    }
}
