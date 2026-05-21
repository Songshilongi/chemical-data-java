package com.songshilong.service.classify.util;

import com.songshilong.module.starter.common.exception.BusinessException;
import com.songshilong.service.classify.enums.ClassifyExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件校验工具类
 *
 * 在文件进入业务逻辑之前进行格式、大小、文件名的校验，
 * 防止无效文件流到 OSS / Python，浪费资源。
 */
@Slf4j
public class FileValidator {

    /** 允许的 Excel 文件扩展名 */
    private static final Set<String> ALLOWED_EXCEL_EXTENSIONS = new HashSet<>(Arrays.asList("xlsx", "xls"));

    /** 允许的模型文件扩展名 */
    private static final Set<String> ALLOWED_MODEL_EXTENSIONS = new HashSet<>(Arrays.asList("zip"));

    /** Excel 文件最大字节数（50MB） */
    private static final long MAX_EXCEL_SIZE = 50L * 1024 * 1024;

    /** 模型文件最大字节数（100MB） */
    private static final long MAX_MODEL_SIZE = 100L * 1024 * 1024;

    /**
     * 校验 Excel 文件
     */
    public static void validateExcelFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ClassifyExceptionEnum.EXCEL_FILE_REQUIRED);
        }
        validateFileName(file);
        validateExtension(file, ALLOWED_EXCEL_EXTENSIONS, ClassifyExceptionEnum.INVALID_EXCEL_FILE);
        validateSize(file, MAX_EXCEL_SIZE, "Excel 文件");
    }

    /**
     * 校验模型文件
     */
    public static void validateModelFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ClassifyExceptionEnum.MODEL_FILE_REQUIRED);
        }
        validateFileName(file);
        validateExtension(file, ALLOWED_MODEL_EXTENSIONS, ClassifyExceptionEnum.INVALID_MODEL_FILE);
        validateSize(file, MAX_MODEL_SIZE, "模型文件");
    }

    /**
     * 校验文件名合法性
     * 防止恶意文件名（路径遍历攻击 / 控制字符）
     */
    private static void validateFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new BusinessException(
                    ClassifyExceptionEnum.FILE_TYPE_NOT_SUPPORTED.errorCode(),
                    "文件名不能为空"
            );
        }
        // 防止路径遍历攻击（../../etc/passwd）
        if (originalFilename.contains("..") || originalFilename.contains("/") || originalFilename.contains("\\")) {
            throw new BusinessException(
                    ClassifyExceptionEnum.FILE_TYPE_NOT_SUPPORTED.errorCode(),
                    "文件名包含非法字符"
            );
        }
    }

    /**
     * 校验扩展名
     */
    private static void validateExtension(MultipartFile file,
                                          Set<String> allowedExtensions,
                                          ClassifyExceptionEnum errorEnum) {
        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename);
        if (extension == null || !allowedExtensions.contains(extension.toLowerCase())) {
            log.warn("文件扩展名校验失败: filename={}, allowed={}", originalFilename, allowedExtensions);
            throw new BusinessException(errorEnum);
        }
    }

    /**
     * 校验文件大小
     */
    private static void validateSize(MultipartFile file, long maxBytes, String fileTypeName) {
        if (file.getSize() > maxBytes) {
            String msg = String.format("%s 大小不能超过 %d MB", fileTypeName, maxBytes / 1024 / 1024);
            log.warn("文件大小超限: filename={}, size={}, max={}",
                    file.getOriginalFilename(), file.getSize(), maxBytes);
            throw new BusinessException(
                    ClassifyExceptionEnum.FILE_TYPE_NOT_SUPPORTED.errorCode(),
                    msg
            );
        }
    }

    /**
     * 提取文件扩展名（不含点）
     */
    private static String getExtension(String filename) {
        if (filename == null) return null;
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == filename.length() - 1) {
            return null;
        }
        return filename.substring(dotIndex + 1);
    }
}
