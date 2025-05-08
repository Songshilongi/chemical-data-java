package com.songshilong.service.compound.controller;

import com.songshilong.module.starter.common.result.Result;
import com.songshilong.service.compound.service.CompoundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.compound.controller
 * @Author: Ice, Song
 * @CreateTime: 2025-05-08  21:30
 * @Description: CompoundController
 * @Version: 1.0
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "化合物SMILES映射查询相关接口")
public class CompoundController {

    private final CompoundService compoundService;


    @GetMapping("/load")
    @ApiOperation(value = "加载SMILES映射表")
    public Result<Boolean> load() {
        compoundService.load();
        return Result.success(Boolean.TRUE);
    }



    @GetMapping("/iupac/{smiles}")
    public Result<String> queryBySmiles(@PathVariable("smiles") String smiles) {
        return Result.success(smiles);
    }

}
