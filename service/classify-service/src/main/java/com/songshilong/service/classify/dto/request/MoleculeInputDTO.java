package com.songshilong.service.classify.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "分子输入类")
public class MoleculeInputDTO {
    /**
     * 输入类型：smiles 或 mol
     */
    @Schema(description = "输入类型：smiles 或 mol")
    private String inputType;

    /**
     * smiles 字符串（inputType = smiles 时填写）
     */
    @Schema(description = "smiles 字符串或 mol 文件内容")
    private String value;
}
