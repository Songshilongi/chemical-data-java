package com.songshilong.service.task.controller;

import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.task.dto.response.MergeChunkResponse;
import com.songshilong.service.task.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/check-file")
    @ApiOperation("检查文件是否已存在（秒传功能）")
    public Result<Boolean> checkFile(
            @ApiParam(value = "文件MD5值") @RequestParam(value = "fileKey") String fileKey,
            @ApiParam(value = "原始文件名") @RequestParam("filename") String filename) {
        return Result.success(uploadService.checkFile(fileKey, filename));
    }

    @PostMapping("/upload-chunk")
    @ApiOperation("上传分片文件")
    public Result<Boolean> uploadChunk(
            @ApiParam(value = "分片文件数据") @RequestParam("file") MultipartFile file,
            @ApiParam(value = "分片文件对应的MD5 key") @RequestParam("fileKey") String fileKey,
            @ApiParam(value = "当前分片索引") @RequestParam("chunk") Integer chunk,
            @ApiParam(value = "总共的分片数量") @RequestParam("total") Integer total,
            @ApiParam(value = "原始文件名") @RequestParam("filename") String filename) {
        return Result.success(uploadService.uploadChunk(file, fileKey, chunk, total, filename));
    }

    @PostMapping("/merge-chunks")
    @ApiOperation("合并分片文件")
    public Result<MergeChunkResponse> mergeChunks(
            @ApiParam(value = "文件唯一标识") @RequestParam("fileKey") String fileKey,
            @ApiParam(value = "总共的分片数量") @RequestParam("total") Integer total,
            @ApiParam(value = "原始文件名    ") @RequestParam("filename") String filename) {
        return Result.success(uploadService.mergeChunks(fileKey, total, filename));
    }

}
