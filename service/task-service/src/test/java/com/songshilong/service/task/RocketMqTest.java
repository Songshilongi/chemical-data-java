package com.songshilong.service.task;

import cn.hutool.core.collection.ListUtil;
import com.songshilong.service.task.dto.StandardReaction;
import com.songshilong.starter.mq.core.RocketMqProducerUtil;
import com.songshilong.starter.mq.enums.RocketMQCenterEnum;
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
    private RocketMqProducerUtil rocketMqProducerUtil;


    @Test
    public void sendMessage() {

        StandardReaction standardReaction = new StandardReaction();
        standardReaction.setReagent(ListUtil.toList("test"));
        standardReaction.setProduct(ListUtil.toList("test"));
        standardReaction.setSolvent(ListUtil.toList("test"));
        standardReaction.setCatalyst(ListUtil.toList("test"));
        standardReaction.setReagent(ListUtil.toList("test"));

        TextTestMessage message = new TextTestMessage();
        message.setData(standardReaction);
        message.setSource("RocketMQ test");
        rocketMqProducerUtil.sendMessage(RocketMQCenterEnum.IE_TEXT.getTopic(), RocketMQCenterEnum.IE_TEXT.getTag(), message);
        rocketMqProducerUtil.sendMessage(RocketMQCenterEnum.IE_SMILES.getTopic(), RocketMQCenterEnum.IE_SMILES.getTag(), message);
        rocketMqProducerUtil.sendMessage(RocketMQCenterEnum.IE_REACTION.getTopic(), RocketMQCenterEnum.IE_REACTION.getTag(), message);
        rocketMqProducerUtil.sendMessage(RocketMQCenterEnum.IE_REAXYS.getTopic(), RocketMQCenterEnum.IE_REAXYS.getTag(), message);
    }

}
