package com.songshilong.service.classify.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.songshilong.service.classify.dao.entity.ClassifySingleDO;
import com.songshilong.service.classify.dto.request.ClassifyPageQueryReqDTO;
import com.songshilong.service.classify.dto.request.ClassifySingleReqDTO;
import com.songshilong.service.classify.dto.response.ClassifyPageQueryRespDTO;


import java.util.List;

public interface ClassifyService extends IService<ClassifySingleDO> {
    /*
    创建分类任务
     */
    void classifySingle(ClassifySingleReqDTO requestParam);

    IPage<ClassifyPageQueryRespDTO> pageQueryClassify(ClassifyPageQueryReqDTO requestParam);

    boolean deleteClassifyTask(List<Long> ids);

}
