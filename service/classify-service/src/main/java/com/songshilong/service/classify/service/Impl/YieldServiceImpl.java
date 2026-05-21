package com.songshilong.service.classify.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.service.classify.context.BaseContext;
import com.songshilong.service.classify.dao.entity.YieldSingleDO;
import com.songshilong.service.classify.dao.mapper.YieldMapper;
import com.songshilong.service.classify.dto.request.MoleculeInputDTO;
import com.songshilong.service.classify.dto.request.YieldPageQueryReqDTO;
import com.songshilong.service.classify.dto.request.YieldSingleReqDTO;
import com.songshilong.service.classify.dto.response.YieldPageQueryRespDTO;
import com.songshilong.service.classify.service.YieldService;
import com.songshilong.service.classify.util.ClassifyUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class YieldServiceImpl extends ServiceImpl<YieldMapper, YieldSingleDO> implements YieldService {

    private final YieldMapper yieldMapper;
    private final ClassifyUpload classifyUpload;
    @Override
    public void yieldSingle(YieldSingleReqDTO requestParam) {
        String smiles;
        // 输入完整的smiles
        if(requestParam.getInputType().equals("smiles") && requestParam.getReactionSmiles() != null){
            smiles = requestParam.getReactionSmiles();
            // 输入分开的smiles
        } else if (requestParam.getInputType().equals("split")) {
            // 校验反应物产物不能为空
            if(requestParam.getReactants() == null || requestParam.getReactants().isEmpty()){
                throw new IllegalArgumentException("反应物不能为空，至少有一个有效输入");
            }
            if(requestParam.getProducts() == null || requestParam.getProducts().isEmpty()){
                throw new IllegalArgumentException("产物不能为空，至少有一个有效输入");
            }
            // 将拆分的 reactants、conditions、products 拼接成 smiles 字符串
            List<String> smilesSegments = new ArrayList<>();
            // 处理反应物
            for (MoleculeInputDTO mol : requestParam.getReactants()) {
                if ("mol".equals(mol.getInputType())) {
                    // 如果是 mol 文件，调用 Python 进行转换
//                    String molSmiles = molToSmiles(mol.getFile());
                    String molSmiles = molToSmiles(mol.getValue());
                    smilesSegments.add(molSmiles);
                } else if ("smiles".equals(mol.getInputType())) {
                    // 如果是 smiles 字符串，直接添加
                    smilesSegments.add(mol.getValue());
                }
            }
            // 处理催化剂
            for (MoleculeInputDTO condition : requestParam.getConditions()) {
                if ("smiles".equals(condition.getInputType())) {
                    smilesSegments.add(condition.getValue());
                } else if ("mol".equals(condition.getInputType())) {
                    String molSmiles = molToSmiles(condition.getValue());
                    smilesSegments.add(molSmiles);
                }
            }
            // 处理产物
            List<String> products = new ArrayList<>();
            for (MoleculeInputDTO mol : requestParam.getProducts()) {
                if ("mol".equals(mol.getInputType())) {
                    String molSmiles = molToSmiles(mol.getValue());
                    products.add(molSmiles);
                } else if ("smiles".equals(mol.getInputType())) {
                    products.add(mol.getValue());
                }
            }
            // 拼接 反应物、催化剂、产物的 SMILES
            String fullSmiles = String.join(".", smilesSegments);
            fullSmiles += ">>" + String.join(".", products);
            smiles = fullSmiles;
        } else {
            throw new RuntimeException("输入类型错误");
        }
        // 调用模型，设置反应产率预测结果为reactionYield
        String reactionYield = predictYield(smiles);
        // 录入数据库
        YieldSingleDO yieldSingleDO = BeanUtil.toBean(requestParam, YieldSingleDO.class);
        yieldSingleDO.setReactionYield(reactionYield);
        yieldSingleDO.setReactionSmiles(smiles);
        yieldSingleDO.setUserId((Long) BaseContext.getContext(Constant.USER_ID));
        yieldMapper.insert(yieldSingleDO);
    }

    @Override
    public IPage<YieldPageQueryRespDTO> pageQueryYield(YieldPageQueryReqDTO requestParam) {
        LambdaQueryWrapper<YieldSingleDO> queryWrapper = Wrappers.lambdaQuery(YieldSingleDO.class)
                .eq(YieldSingleDO::getUserId, BaseContext.getContext(Constant.USER_ID))
                .like(StrUtil.isNotBlank(requestParam.getReactionSmiles()), YieldSingleDO::getReactionSmiles, requestParam.getReactionSmiles());
        IPage<YieldSingleDO> selectPage = yieldMapper.selectPage(requestParam, queryWrapper);
        return selectPage.convert(each -> BeanUtil.toBean(each, YieldPageQueryRespDTO.class));
    }

    @Override
    public boolean deleteYieldTask(List<Long> ids) {
        int deletedCount = yieldMapper.deleteBatchIds(ids);
        return deletedCount > 0;
    }

    private String molToSmiles(String molString) {
        // TODO:调用 Python 服务进行 mol 文件转 smiles
        //        return pythonService.convertMolToSmiles(molFile);
        return "mol";
    }

    private String predictYield(String Smiles) {
        // TODO:调用 Python 脚本进行预测
        return "python算法"+Smiles+'%';
    }
}
