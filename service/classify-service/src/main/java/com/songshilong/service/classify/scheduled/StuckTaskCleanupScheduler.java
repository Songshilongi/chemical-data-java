package com.songshilong.service.classify.scheduled;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.songshilong.service.classify.dao.entity.ClassifyBatchDO;
import com.songshilong.service.classify.dao.mapper.ClassifyBatchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 僵尸任务清理定时器
 *
 * 任务"僵尸"的来源：
 * 1. Python 服务异常退出，导致任务无回调
 * 2. MQ 消息丢失（小概率）
 * 3. Java 重启时正在处理的任务
 * 4. 其他未知的服务异常
 *
 * 这些任务的 status 会永远停留在 SUBMITTED/PROCESSING，前端看不出区别于"正常处理中"。
 * 本定时器定期扫描，将超时未完成的任务标记为 FAILED，避免：
 * - 前端无限轮询
 * - 用户陷入"永远等待"
 * - 任务统计数据失真
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StuckTaskCleanupScheduler {

    private final ClassifyBatchMapper classifyBatchMapper;

    /** 任务超时阈值：超过该时间还未完成的视为僵尸 */
    private static final int TIMEOUT_MINUTES = 30;

    /** 待清理的状态列表 */
    private static final List<String> PENDING_STATUSES = Arrays.asList("SUBMITTED", "PROCESSING");

    /**
     * 每 10 分钟执行一次（启动后延迟 1 分钟开始）
     *
     * cron 表达式说明：
     * - 0 0/10 * * * ?  → 每小时的 0, 10, 20, 30, 40, 50 分各执行一次
     *
     * 也可以用更简单的 fixedDelay = 600_000（毫秒）
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void cleanupStuckTasks() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(TIMEOUT_MINUTES);

        LambdaUpdateWrapper<ClassifyBatchDO> wrapper = Wrappers.lambdaUpdate(ClassifyBatchDO.class)
                .in(ClassifyBatchDO::getStatus, PENDING_STATUSES)
                .lt(ClassifyBatchDO::getCreateTime, threshold)
                .eq(ClassifyBatchDO::getDeleted, 0)
                .set(ClassifyBatchDO::getStatus, "FAILED")
                .set(ClassifyBatchDO::getRemark,
                        "任务超时未完成（已超过 " + TIMEOUT_MINUTES + " 分钟），系统自动标记为失败")
                .set(ClassifyBatchDO::getUpdateTime, LocalDateTime.now());

        int affected = classifyBatchMapper.update(null, wrapper);

        if (affected > 0) {
            log.warn("[僵尸清理] 已将 {} 个超时任务标记为 FAILED（阈值: {}分钟）",
                    affected, TIMEOUT_MINUTES);
        } else {
            log.debug("[僵尸清理] 无超时任务，运行正常");
        }
    }
}
