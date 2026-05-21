package com.songshilong.service.classify.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ClassifyBatchReqDTO {

    @Schema(description = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    @Size(max = 100, message = "任务名称长度不能超过100")
    private String taskName;

    @Schema(description = "使用的预测模型名称")
    @Size(max = 200, message = "模型名称长度不能超过200")
    private String predictModel;

    @Schema(description = "是否为用户上传模型（0: 用户上传，1: 使用系统模型）")
    @NotNull(message = "customModel 不能为空")
    private Integer customModel;

    @Schema(description = "用户上传的模型文件（如果选择上传模型）")
    private MultipartFile modelFile;

    @Schema(description = "批量预测时上传的 Excel 文件")
    private MultipartFile excelFile;
}
