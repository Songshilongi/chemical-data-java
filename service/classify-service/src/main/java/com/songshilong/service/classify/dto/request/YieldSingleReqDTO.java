package com.songshilong.service.classify.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Schema(description = "单个smiles预测任务")
public class YieldSingleReqDTO {

    @Schema(description = "输入方式：smiles / split")
    private String inputType;

    @Schema(description = "当 inputType = smiles 时填写")
    private String reactionSmiles;

    @Schema(description = "当 inputType = split 时填写：反应物")
    private List<MoleculeInputDTO> reactants;

    @Schema(description = "拆分输入：反应条件")
    private List<MoleculeInputDTO> conditions;

    @Schema(description = "拆分输入：产物")
    private List<MoleculeInputDTO> products;
}