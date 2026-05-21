package com.songshilong.service.classify.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.songshilong.service.classify.dao.entity.ClassifyBatchDO;
import com.songshilong.service.classify.dto.request.ClassifyBatchReqDTO;
import com.songshilong.service.classify.dto.request.ClassifyPageBatchQueryReqDTO;
import com.songshilong.service.classify.dto.response.ClassifyPageBatchQueryRespDTO;

import java.util.List;

public interface ClassifyBatchService extends IService<ClassifyBatchDO> {
    void batchClassify(ClassifyBatchReqDTO requestParam);

    IPage<ClassifyPageBatchQueryRespDTO> pageBatchQueryClassify(ClassifyPageBatchQueryReqDTO requestParam);

    boolean deleteClassifyTask(List<Long> ids);
}
