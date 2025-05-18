package com.songshilong.service.task.service.impl;

import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.module.starter.common.constant.RedisKeyConstant;
import com.songshilong.module.starter.common.enums.ClientExceptionEnum;
import com.songshilong.module.starter.common.exception.ClientException;
import com.songshilong.service.task.context.BaseContext;
import com.songshilong.service.task.service.UploadService;
import com.songshilong.service.task.util.AliOssUtil;
import com.songshilong.starter.cache.core.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.service.impl
 * @Author: Ice, Song
 * @CreateTime: 2025-05-18  18:50
 * @Description: UploadServiceImpl
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {


    private final AliOssUtil aliOssUtil;
    private final RedisUtil redisUtil;


    private static final String TEMPLATE_TEXT_RESOURCE_PATH = "template/template_text.xlsx";
    private static final Resource TEXT_TEMPLATE_RESOURCE;

    static {
        TEXT_TEMPLATE_RESOURCE = new ClassPathResource(TEMPLATE_TEXT_RESOURCE_PATH);
    }

    @Override
    public ResponseEntity<Resource> getTextTemplate() {
        if (!TEXT_TEMPLATE_RESOURCE.exists()) {
            return ResponseEntity.notFound().build();
        }
        ResponseEntity<Resource> result = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode("文本抽取批量处理模板.xlsx", "UTF-8"));
            result = ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(TEXT_TEMPLATE_RESOURCE);
        } catch (IOException e) {
            throw new ClientException(ClientExceptionEnum.TEMPLATE_RESOURCE_LOAD_FAIL);
        }
        return result;
    }


    @Override
    public Boolean checkFile(String fileKey, String filename) {
        String value = filename + "|" + fileKey;
        int location = calculateSlot(value);
        String key = RedisKeyConstant.CHUNK_UPLOAD_PREFIX + location;
        return redisUtil.setIsMember(key, value);
    }


    @Override
    public Boolean uploadChunk(MultipartFile file, String fileKey, Integer chunk, Integer total, String filename) {
        String ossKey = String.format("chunks/%s/%s/%d.part", BaseContext.getContext(Constant.USERNAME), fileKey, chunk);


        return Boolean.TRUE;
    }

    @Override
    public Boolean mergeChunks(String fileKey, Integer total, String filename) {
        return Boolean.TRUE;
    }

    /**
     * 计算已经上传的文件key的Redis Set分片位置
     * hash % 16 = hash & (16 - 1)
     * 当分片数 n 是 2 的幂时，hash % n 等价于 hash & (n-1)。
     *
     * @param value 需要被计算的value
     * @return 位置
     */
    private int calculateSlot(String value) {
        return (value.hashCode() & 0x7FFFFFFF) & 15;
    }
}
