package com.songshilong.service.task;

import cn.hutool.core.collection.ListUtil;
import com.songshilong.service.task.dto.StandardReaction;
import com.songshilong.starter.mq.core.RocketMqUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task
 * @Author: Ice, Song
 * @CreateTime: 2025-04-15  21:44
 * @Description: RocketMqTest
 * @Version: 1.0
 */
@SpringBootTest
public class RocketMqTest {

    @Autowired
    private RocketMqUtil rocketMqUtil;


    @Test
    public void sendMessage() {
        String topic = "task-service-IE";
        String tag = "text";
        StandardReaction standardReaction = new StandardReaction();
        standardReaction.setReagent(ListUtil.toList("test"));
        standardReaction.setProduct(ListUtil.toList("test"));
        standardReaction.setSolvent(ListUtil.toList("test"));
        standardReaction.setCatalyst(ListUtil.toList("test"));
        standardReaction.setReagent(ListUtil.toList("test"));
        rocketMqUtil.sendMessage(topic, tag, standardReaction);
    }

}
