package com.songshilong.service.compound.controller;

import com.songshilong.module.starter.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
public class CompoundController {



    @GetMapping("/iupac/{smiles}")
    public Result<String> queryBySmiles(@PathVariable("smiles") String smiles) {
        return Result.success(smiles);
    }

}
