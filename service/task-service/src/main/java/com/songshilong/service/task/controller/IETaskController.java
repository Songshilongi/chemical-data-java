package com.songshilong.service.task.controller;

import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.controller
 * @Author: Ice, Song
 * @CreateTime: 2025-03-31  16:33
 * @Description: TaskController
 * @Version: 1.0
 */
@RestController("/ie")
@RequiredArgsConstructor
@Api(tags = "任务相关接口")
public class IETaskController {
    private final TaskService taskService;



    @PostMapping("/create/{type}")
    @ApiOperation(value = "创建任务")
    public Result<Boolean> createTask(@PathVariable String type,
                                                 @RequestParam(required = false) String chemicalText,
                                                 @RequestParam(required = false) MultipartFile[] files) {
        taskService.createTask(type, chemicalText, files);
        return Result.success(Boolean.TRUE);
    }
}
