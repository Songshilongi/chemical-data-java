package com.songshilong.service.classify.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.songshilong.service.classify.dao.entity.YieldBatchDO;
import com.songshilong.service.classify.dto.request.YieldBatchReqDTO;
import com.songshilong.service.classify.dto.request.YieldPageBatchQueryReqDTO;
import com.songshilong.service.classify.dto.response.YieldPageBatchQueryRespDTO;

import java.util.List;

public interface YieldBatchService extends IService<YieldBatchDO> {
    void batchYield(YieldBatchReqDTO requestParam);

    IPage<YieldPageBatchQueryRespDTO> pageBatchQueryYield(YieldPageBatchQueryReqDTO requestParam);

    boolean deleteYieldTask(List<Long> ids);
}
