package com.songshilong.starter.mq.core;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import springfox.documentation.spring.web.json.Json;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.mq.core
 * @Author: Ice, Song
 * @CreateTime: 2025-04-15  21:26
 * @Description: RocketMqUtil
 * @Version: 1.0
 */
@RequiredArgsConstructor
public class RocketMqUtil {

    private final RocketMQTemplate rocketMQTemplate;


    public void sendMessage(String topic, Object data) {
        rocketMQTemplate.convertAndSend(topic, data);
    }


    public void sendMessage(String topic, String tag, Object data) {
        rocketMQTemplate.convertAndSend(String.format("%s:%s", topic, tag), data);
    }

    public void sendMessageWithTransaction(String topic, Object data) {
        Message<?> message = MessageBuilder.withPayload(data).build();
        rocketMQTemplate.sendMessageInTransaction(topic, message, null);
    }

    public void sendMessageWithTransaction(String topic, String tag, Object data) {
        MessageBuilder<Object> messageBuilder = MessageBuilder.withPayload(data).setHeader("msg", JSON.toJSONString(data));
        rocketMQTemplate.sendMessageInTransaction(String.format("%s:%s", topic, tag), messageBuilder.build(), null);
    }
}
