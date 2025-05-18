package com.songshilong.service.task.controller;

import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.task.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.controller
 * @Author: Ice, Song
 * @CreateTime: 2025-05-18  18:49
 * @Description: 上传功能
 * @Version: 1.0
 */
@RequiredArgsConstructor
@RestController("/batch")
@Api("批量上传处理任务相关接口")
public class UploadController {

    private final UploadService uploadService;



    @GetMapping("/template/text")
    @ApiOperation("获取文本批量处理的excel模版")
    public ResponseEntity<Resource> getTextTemplate() {
        return uploadService.getTextTemplate();
    }

}
