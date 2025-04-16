package com.songshilong.service.task;

import com.songshilong.service.task.dto.StandardReaction;
import com.songshilong.starter.mq.core.BaseMessage;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task
 * @Author: Ice, Song
 * @CreateTime: 2025-04-16  20:48
 * @Description: TextTestMessage
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TextTestMessage extends BaseMessage {

    private StandardReaction data;

}
