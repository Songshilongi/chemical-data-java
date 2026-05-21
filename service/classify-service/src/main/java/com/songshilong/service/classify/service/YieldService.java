package com.songshilong.service.classify.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.songshilong.service.classify.dao.entity.YieldSingleDO;
import com.songshilong.service.classify.dto.request.YieldPageQueryReqDTO;
import com.songshilong.service.classify.dto.request.YieldSingleReqDTO;
import com.songshilong.service.classify.dto.response.YieldPageQueryRespDTO;

import java.util.List;

public interface YieldService extends IService<YieldSingleDO> {
    void yieldSingle(YieldSingleReqDTO requestParam);

    IPage<YieldPageQueryRespDTO> pageQueryYield(YieldPageQueryReqDTO requestParam);

    boolean deleteYieldTask(List<Long> ids);
}
