package com.songshilong.starter.mq.core;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.mq.core
 * @Author: Ice, Song
 * @CreateTime: 2025-04-15  21:26
 * @Description: RocketMqUtil
 * @Version: 1.0
 */
@RequiredArgsConstructor
@Slf4j
public class RocketMqProducerUtil {

    private final RocketMQTemplate rocketMQTemplate;

    /**
     * send Message to topic
     * @param topic MQ topic
     * @param data payload data
     */
    public void sendMessage(String topic, Object data) {
        rocketMQTemplate.convertAndSend(topic, data);
        log.info("send message to rocketmq success, topic:{}, data:{}", topic, JSON.toJSONString(data));
    }

    /**
     * send Message to topic and tag
     * @param topic MQ topic
     * @param tag MQ tag
     * @param data payload data
     */
    public void sendMessage(String topic, String tag, Object data) {
        rocketMQTemplate.convertAndSend(String.format("%s:%s", topic, tag), data);
        log.info("send message to rocketmq success, topic:{}, tag:{}, data:{}", topic, tag, JSON.toJSONString(data));
    }
    /**
     * send Message to topic with Transaction
     * @param topic MQ topic
     * @param data payload data
     */
    public void sendMessageWithTransaction(String topic, Object data) {
        Message<?> message = MessageBuilder.withPayload(data).build();
        rocketMQTemplate.sendMessageInTransaction(topic, message, null);
        log.info("send message with transaction to rocketmq success, topic:{}, data:{}", topic, JSON.toJSONString(data));
    }
    /**
     * send Message to topic and tag with transaction
     * @param topic MQ topic
     * @param tag MQ tag
     * @param data payload data
     */
    public void sendMessageWithTransaction(String topic, String tag, Object data) {
        MessageBuilder<Object> messageBuilder = MessageBuilder.withPayload(data).setHeader("msg", JSON.toJSONString(data));
        rocketMQTemplate.sendMessageInTransaction(String.format("%s:%s", topic, tag), messageBuilder.build(), null);
        log.info("send message with transaction to rocketmq success, topic:{}, tag:{}, data:{}", topic, tag, JSON.toJSONString(data));
    }
}
