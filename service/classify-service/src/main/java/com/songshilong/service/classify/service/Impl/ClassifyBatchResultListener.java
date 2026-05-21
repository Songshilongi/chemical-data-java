package com.songshilong.service.classify.service.Impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.songshilong.service.classify.dao.entity.ClassifyBatchDO;
import com.songshilong.service.classify.dao.mapper.ClassifyBatchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = "reaction-batch-result-topic",
        consumerGroup = "reaction-result-consumer-group"
)
public class ClassifyBatchResultListener implements RocketMQListener<String> {

    private final ClassifyBatchMapper classifyBatchMapper;

    /** 终态：已经处于这些状态的任务，不允许被结果消息覆盖（防止重复消费/乱序消费） */
    private static final List<String> TERMINAL_STATUSES = Arrays.asList("SUCCESS", "FAILED");

    @Override
    public void onMessage(String message) {
        log.info("[MQ结果] 收到批量分类任务结果: {}", message);
        try {
            JSONObject result = JSONObject.parseObject(message);
            String jobId = result.getString("jobId");
            String status = result.getString("status");

            if (jobId == null || status == null) {
                log.error("[MQ结果] 消息格式错误，缺少 jobId 或 status: {}", message);
                return;
            }

            // 1. 查任务（用于日志和返回值判断）
            ClassifyBatchDO task = classifyBatchMapper.selectOneByJobId(jobId);
            if (task == null) {
                log.error("[MQ结果] 根据 jobId 未找到任务记录: {}", jobId);
                return;
            }

            // 2. 幂等检查：如果已经是终态，记录日志后跳过
            if (TERMINAL_STATUSES.contains(task.getStatus())) {
                log.warn("[MQ结果-幂等] 任务已处于终态 ({}), 跳过本次更新。jobId={}, 收到的状态={}",
                        task.getStatus(), jobId, status);
                return;
            }

            // 3. 构造更新条件：必须 jobId 匹配 + 当前状态非终态
            //    这是关键的幂等防护：即使并发多个消息同时到达，也只有一个能成功更新
            LambdaUpdateWrapper<ClassifyBatchDO> updateWrapper = Wrappers.lambdaUpdate(ClassifyBatchDO.class)
                    .eq(ClassifyBatchDO::getJobId, jobId)
                    .notIn(ClassifyBatchDO::getStatus, TERMINAL_STATUSES)
                    .set(ClassifyBatchDO::getUpdateTime, LocalDateTime.now());

            if (Objects.equals(status, "SUCCESS")) {
                updateWrapper
                        .set(ClassifyBatchDO::getStatus, "SUCCESS")
                        .set(ClassifyBatchDO::getExcelDownload, result.getString("outputUrl"))
                        .set(ClassifyBatchDO::getRemark, "处理成功");
            } else {
                String errorMsg = result.getString("error");
                if (errorMsg != null && errorMsg.length() > 1000) {
                    errorMsg = errorMsg.substring(0, 1000) + "...(已截断)";
                }
                updateWrapper
                        .set(ClassifyBatchDO::getStatus, "FAILED")
                        .set(ClassifyBatchDO::getRemark, errorMsg);
            }

            int affected = classifyBatchMapper.update(null, updateWrapper);

            if (affected > 0) {
                log.info("[MQ结果] 任务状态更新成功, jobId={}, 新状态={}", jobId, status);
            } else {
                // affected = 0 通常意味着：
                // 1. 任务在我们 select 之后到 update 之前，被另一个消费者更新到了终态（并发场景）
                // 2. 任务已被软删除
                log.warn("[MQ结果-幂等] update 影响 0 行，可能是并发场景下被其他消息抢先更新。jobId={}", jobId);
            }

        } catch (Exception e) {
            log.error("[MQ结果] 处理批量分类结果消息时发生异常", e);
            // 不抛异常，避免 MQ 触发重试导致死循环
            // 业务上的失败已经通过日志和监控系统暴露
        }
    }
}
