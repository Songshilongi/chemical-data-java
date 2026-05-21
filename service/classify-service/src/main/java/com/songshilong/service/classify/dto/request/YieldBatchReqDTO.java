package com.songshilong.service.classify.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class YieldBatchReqDTO {
    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "批量预测时上传的 Excel 文件")
    private MultipartFile excelFile;
}
