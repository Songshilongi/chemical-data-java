package com.songshilong.service.classify.enums;

import com.songshilong.module.starter.common.enums.ExceptionHandler;

/**
 * 反应分类服务的业务异常枚举
 * 错误码段位约定：70001-79999 留给 classify 服务
 */
public enum ClassifyExceptionEnum implements ExceptionHandler {

    // ========== 文件相关 70001-70099 ==========
    EXCEL_FILE_REQUIRED(70001, "请上传 Excel 文件"),
    MODEL_FILE_REQUIRED(70002, "请上传自定义模型文件"),
    FILE_UPLOAD_FAIL(70003, "文件上传失败，请稍后重试"),
    FILE_TYPE_NOT_SUPPORTED(70004, "文件类型不支持"),
    INVALID_EXCEL_FILE(70005, "Excel 文件格式错误，仅支持 .xlsx 格式"),
    INVALID_MODEL_FILE(70006, "模型文件格式错误，仅支持 .zip 格式"),

    // ========== 模型相关 70100-70199 ==========
    INVALID_MODEL_TYPE(70100, "模型选择错误"),
    MODEL_NOT_FOUND(70101, "未找到指定模型"),

    // ========== SMILES 输入相关 70200-70299 ==========
    REACTION_SMILES_REQUIRED(70200, "反应 SMILES 不能为空"),
    INVALID_REACTION_SMILES(70201, "反应 SMILES 格式不正确"),

    // ========== Python 服务相关 70300-70399 ==========
    PYTHON_SERVICE_UNAVAILABLE(70300, "预测服务暂时不可用，请稍后重试"),
    PYTHON_PREDICT_FAIL(70301, "预测失败"),
    PYTHON_RESPONSE_INVALID(70302, "预测服务返回数据格式错误"),

    // ========== MQ 相关 70400-70499 ==========
    MQ_SEND_FAIL(70400, "任务提交失败，请稍后重试"),

    // ========== 任务相关 70500-70599 ==========
    TASK_DELETE_FAIL(70500, "任务删除失败"),
    ;

    private final Integer errorCode;
    private final String errorMsg;

    ClassifyExceptionEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public Integer errorCode() {
        return this.errorCode;
    }

    @Override
    public String errorMsg() {
        return this.errorMsg;
    }
}
