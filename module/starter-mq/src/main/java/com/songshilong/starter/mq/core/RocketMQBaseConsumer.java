package com.songshilong.starter.mq.core;

import com.songshilong.module.starter.common.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.mq.core
 * @Author: Ice, Song
 * @CreateTime: 2025-04-16  20:13
 * @Description: RocketMQBaseConsumer
 * @Version: 1.0
 */
@RequiredArgsConstructor
public abstract class RocketMQBaseConsumer<T extends BaseMessage> {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected final RocketMQTemplate rocketMQTemplate;


    /**
     * consume message logic
     * @param message received message
     */
    protected abstract void handlerMessage(T message);

    /**
     * get consumer name
     * @return consumer name
     */
    protected abstract String getConsumerName();

    /**
     * whether filter message
     * @param message MQ message
     * @return default -false
     */
    protected boolean isFilter(T message){
        return false;
    }

    protected void onMessage(T message) {
        String consumerName = this.getConsumerName();
        log.info("consumer - [{}] - receive the message, data: {}", consumerName, BeanUtil.toJSON(message));
        if (this.isFilter(message)) {
            log.info("message filtered");
            return;
        }
        try {
            long start = Instant.now().toEpochMilli();
            handlerMessage(message);
            long end = Instant.now().toEpochMilli();
            log.info("consumer - [{}] - handle the message success, cost: {}ms", consumerName, end - start);
        } catch (Exception e) {
            log.error("consumer - [{}] - handle the message error, message: {}", consumerName, BeanUtil.toJSON(message), e);
        }
    }

}
