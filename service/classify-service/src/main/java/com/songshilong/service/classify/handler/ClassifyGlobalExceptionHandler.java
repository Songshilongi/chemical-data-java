package com.songshilong.service.classify.handler;

import com.songshilong.module.starter.common.exception.BusinessException;
import com.songshilong.module.starter.common.exception.ClientException;
import com.songshilong.module.starter.common.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * 分类服务的全局异常处理器
 *
 * 注意：本类不继承 starter-web 的 GlobalExceptionHandler，
 * 而是通过 ClassifyWebConfiguration 中手动注册一个空的 GlobalExceptionHandler Bean，
 * 让 starter 的 @ConditionalOnMissingBean 不再创建默认 advice，
 * 从而避免"同类型 Bean 重复"和"@ExceptionHandler 歧义"问题。
 */
@Slf4j
@RestControllerAdvice
public class ClassifyGlobalExceptionHandler {

    /**
     * 业务异常 - 透传 errorCode 给前端
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Object> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.warn("[业务异常] URI={}, code={}, msg={}",
                request.getRequestURI(), ex.errorCode(), ex.errorMsg());
        return Result.fail(ex.errorCode(), ex.errorMsg(), null);
    }

    /**
     * 客户端异常 - 透传 errorCode 给前端
     */
    @ExceptionHandler(ClientException.class)
    public Result<Object> handleClientException(ClientException ex, HttpServletRequest request) {
        log.warn("[客户端异常] URI={}, code={}, msg={}",
                request.getRequestURI(), ex.errorCode(), ex.errorMsg());
        return Result.fail(ex.errorCode(), ex.errorMsg(), null);
    }

    /**
     * @RequestParam 必填参数缺失
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Object> handleMissingParameter(MissingServletRequestParameterException ex,
                                                 HttpServletRequest request) {
        String msg = "缺少必填参数: " + ex.getParameterName();
        log.warn("[参数缺失] URI={}, msg={}", request.getRequestURI(), msg);
        return Result.fail(400, msg, null);
    }

    /**
     * @RequestPart 必传的 multipart 文件部分缺失
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public Result<Object> handleMissingPart(MissingServletRequestPartException ex,
                                            HttpServletRequest request) {
        String msg = "缺少必传文件: " + ex.getRequestPartName();
        log.warn("[文件缺失] URI={}, msg={}", request.getRequestURI(), msg);
        return Result.fail(400, msg, null);
    }

    /**
     * 文件上传超出大小限制
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<Object> handleMaxUploadSize(MaxUploadSizeExceededException ex,
                                              HttpServletRequest request) {
        log.warn("[文件超限] URI={}", request.getRequestURI());
        return Result.fail(400, "上传文件大小超过限制", null);
    }

    /**
     * IllegalArgumentException：参数非法
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Object> handleIllegalArgument(IllegalArgumentException ex,
                                                HttpServletRequest request) {
        log.warn("[参数错误] URI={}, msg={}", request.getRequestURI(), ex.getMessage());
        return Result.fail(400, ex.getMessage(), null);
    }

    /**
     * IllegalStateException：状态错误（比如用户身份缺失）
     */
    @ExceptionHandler(IllegalStateException.class)
    public Result<Object> handleIllegalState(IllegalStateException ex,
                                             HttpServletRequest request) {
        log.warn("[状态错误] URI={}, msg={}", request.getRequestURI(), ex.getMessage());
        return Result.fail(401, ex.getMessage(), null);
    }

    /**
     * 兜底：所有未捕获的异常
     */
    @ExceptionHandler(Throwable.class)
    public Result<Object> handleThrowable(Throwable ex, HttpServletRequest request) {
        log.error("[系统异常] URI={}", request.getRequestURI(), ex);
        return Result.fail(500, "系统繁忙，请稍后重试", null);
    }

    /**
     * @Valid 校验 @RequestBody / @ModelAttribute 失败时抛出
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                       HttpServletRequest request) {
        // 取第一个错误信息作为提示（用户一次只看一个错误更清晰）
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getDefaultMessage())
                .orElse("参数校验失败");
        log.warn("[参数校验失败] URI={}, msg={}", request.getRequestURI(), msg);
        return Result.fail(400, msg, null);
    }

    /**
     * @Validated 校验 @RequestParam / @PathVariable 失败时抛出
     */
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public Result<Object> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex,
                                                    HttpServletRequest request) {
        String msg = ex.getConstraintViolations().stream()
                .findFirst()
                .map(v -> v.getMessage())
                .orElse("参数校验失败");
        log.warn("[参数校验失败] URI={}, msg={}", request.getRequestURI(), msg);
        return Result.fail(400, msg, null);
    }

    /**
     * 表单绑定失败（@ModelAttribute）
     */
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public Result<Object> handleBindException(org.springframework.validation.BindException ex,
                                              HttpServletRequest request) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getDefaultMessage())
                .orElse("参数绑定失败");
        log.warn("[参数绑定失败] URI={}, msg={}", request.getRequestURI(), msg);
        return Result.fail(400, msg, null);
    }

}
