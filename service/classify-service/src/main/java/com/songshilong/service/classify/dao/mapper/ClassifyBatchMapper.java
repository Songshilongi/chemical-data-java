package com.songshilong.service.classify.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.songshilong.service.classify.dao.entity.ClassifyBatchDO;

public interface ClassifyBatchMapper extends BaseMapper<ClassifyBatchDO> {
    /**
     * 根据JobId查询任务
     * @param jobId 任务ID
     * @return 任务实体
     */
    default ClassifyBatchDO selectOneByJobId(String jobId) {
        return this.selectOne(Wrappers.lambdaQuery(ClassifyBatchDO.class)
                .eq(ClassifyBatchDO::getJobId, jobId));
    }
}
