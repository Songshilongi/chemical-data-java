package com.songshilong.service.classify.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.songshilong.module.starter.common.idempotent.NoDuplicateSubmit;
import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.classify.dto.request.ClassifyPageQueryReqDTO;
import com.songshilong.service.classify.dto.request.ClassifySingleReqDTO;
import com.songshilong.service.classify.dto.request.MoleculeInputDTO;
import com.songshilong.service.classify.dto.response.ClassifyPageQueryRespDTO;
import com.songshilong.service.classify.service.ClassifyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated   // ← 类上加这个，启用 @RequestParam 的校验
@Tag(name = "反应分类")
public class ClassifyController {
    private final ClassifyService classifyService;

    @NoDuplicateSubmit(message = "请勿短时间内重复提交反应类型预测任务")
    @PostMapping(value="/api/classify-task/predict/create-single",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "提交反应分类任务")
    public Result<Boolean> reactionClassify(
            @RequestParam("inputType")
            @NotBlank(message = "输入方式不能为空")
            @Pattern(regexp = "^(smiles|split)$", message = "输入方式只能是 smiles 或 split")
            String inputType,

            @RequestParam(value = "reactionSmiles", required = false)
            @Size(max = 5000, message = "反应SMILES长度不能超过5000")
            String reactionSmiles,

            @RequestPart(value = "reactants", required = false) List<MoleculeInputDTO> reactants,
            @RequestPart(value = "conditions", required = false) List<MoleculeInputDTO> conditions,
            @RequestPart(value = "products", required = false) List<MoleculeInputDTO> products,

            @RequestParam("predictModel")
            @Size(max = 200, message = "模型名称长度不能超过200")
            String predictModel,

            @RequestPart(value = "modelFile", required = false) MultipartFile modelFile,

            @RequestParam("customModel")
            @NotNull(message = "customModel 不能为空")
            Integer customModel
    ) {
        ClassifySingleReqDTO requestParam = new ClassifySingleReqDTO();
        requestParam.setInputType(inputType);
        requestParam.setReactionSmiles(reactionSmiles);
        requestParam.setReactants(reactants);
        requestParam.setConditions(conditions);
        requestParam.setProducts(products);
        requestParam.setPredictModel(predictModel);
        requestParam.setModelFile(modelFile);
        requestParam.setCustomModel(customModel);
        classifyService.classifySingle(requestParam);
        return Result.success(Boolean.TRUE);
    }

    @Operation(summary = "分页查询反应分类历史记录")
    @GetMapping("/api/classify-task/predict/query-page")
    public Result<IPage<ClassifyPageQueryRespDTO>> pageQueryClassify(
            @Valid ClassifyPageQueryReqDTO requestParam) {
        return Result.success(classifyService.pageQueryClassify(requestParam));
    }

    @Operation(summary = "删除反应分类任务")
    @DeleteMapping("/api/classify-task/predict/delete")
    public Result<Boolean> deleteClassifyTask(@RequestBody List<Long> ids) {
        boolean isDeleted = classifyService.deleteClassifyTask(ids);
        if (isDeleted) {
            return Result.success(Boolean.TRUE);
        } else {
            return Result.fail(500,"删除失败",  Boolean.FALSE);
        }
    }
}
