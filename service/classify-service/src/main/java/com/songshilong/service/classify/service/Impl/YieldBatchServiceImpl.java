package com.songshilong.service.classify.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.service.classify.context.BaseContext;
import com.songshilong.service.classify.dao.entity.YieldBatchDO;
import com.songshilong.service.classify.dao.mapper.YieldBatchMapper;
import com.songshilong.service.classify.dto.request.YieldBatchReqDTO;
import com.songshilong.service.classify.dto.request.YieldPageBatchQueryReqDTO;
import com.songshilong.service.classify.dto.response.YieldPageBatchQueryRespDTO;
import com.songshilong.service.classify.service.YieldBatchService;
import com.songshilong.service.classify.util.ClassifyUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class YieldBatchServiceImpl extends ServiceImpl<YieldBatchMapper, YieldBatchDO> implements YieldBatchService {

    private final YieldBatchMapper yieldBatchMapper;
    private final ClassifyUpload classifyUpload;

    @Override
    public void batchYield(YieldBatchReqDTO requestParam) {
        String excelUploadURL;
        // 验证excel文件是否上传
        if(requestParam.getExcelFile() == null){
            throw new RuntimeException("excel文件未上传");
        } else {
            try {
                Map<String, String> excelUpload = classifyUpload.uploadFile(requestParam.getExcelFile());
                excelUploadURL = excelUpload.get("fileUrl");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // TODO 模型已经确定，上传excel文件去预测，返回预测好的excel
        String excelDownload = predictYieldBatch(excelUploadURL);
        YieldBatchDO yieldBatchDO = BeanUtil.toBean(requestParam, YieldBatchDO.class);
        yieldBatchDO.setUserId((Long) BaseContext.getContext(Constant.USER_ID));
        yieldBatchDO.setExcelDownload(excelDownload);
        yieldBatchMapper.insert(yieldBatchDO);
    }

    @Override
    public IPage<YieldPageBatchQueryRespDTO> pageBatchQueryYield(YieldPageBatchQueryReqDTO requestParam) {
        LambdaQueryWrapper<YieldBatchDO> queryWrapper = Wrappers.lambdaQuery(YieldBatchDO.class)
                .eq(YieldBatchDO::getUserId, BaseContext.getContext(Constant.USER_ID))
                .like(StrUtil.isNotBlank(requestParam.getTaskName()), YieldBatchDO::getTaskName, requestParam.getTaskName());
        IPage<YieldBatchDO> selectPage = yieldBatchMapper.selectPage(requestParam, queryWrapper);
        return selectPage.convert(each -> BeanUtil.toBean(each, YieldPageBatchQueryRespDTO.class));
    }

    @Override
    public boolean deleteYieldTask(List<Long> ids) {
        int deletedCount = yieldBatchMapper.deleteBatchIds(ids);
        return deletedCount > 0;
    }

    private String predictYieldBatch(String excelUploadURL) {
        return excelUploadURL;
    }
}
