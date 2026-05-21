package com.songshilong.service.classify.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Schema(description = "单个smiles预测任务")
public class ClassifySingleReqDTO {

    @Schema(description = "输入方式：smiles / split")
    @NotBlank(message = "输入方式不能为空")
    @Pattern(regexp = "^(smiles|split)$", message = "输入方式只能是 smiles 或 split")
    private String inputType;

    @Schema(description = "当 inputType = smiles 时填写")
    @Size(max = 5000, message = "反应SMILES长度不能超过5000")
    private String reactionSmiles;

    @Schema(description = "当 inputType = split 时填写：反应物")
    private List<MoleculeInputDTO> reactants;

    @Schema(description = "拆分输入：反应条件")
    private List<MoleculeInputDTO> conditions;

    @Schema(description = "拆分输入：产物")
    private List<MoleculeInputDTO> products;

    @Schema(description = "使用的预测模型名称")
    @Size(max = 200, message = "模型名称长度不能超过200")
    private String predictModel;

    @Schema(description = "用户上传的模型文件（如果选择上传模型）")
    private MultipartFile modelFile;

    @Schema(description = "是否为用户上传模型（0: 用户上传，1: 使用系统模型）")
    @NotNull(message = "customModel 不能为空")
    private Integer customModel;
}
