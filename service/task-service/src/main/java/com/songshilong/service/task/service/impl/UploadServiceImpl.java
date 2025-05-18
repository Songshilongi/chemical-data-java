package com.songshilong.service.task.service.impl;

import com.songshilong.module.starter.common.enums.ClientExceptionEnum;
import com.songshilong.module.starter.common.exception.ClientException;
import com.songshilong.service.task.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
}
