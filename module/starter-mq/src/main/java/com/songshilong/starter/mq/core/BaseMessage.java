package com.songshilong.starter.mq.core;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.mq.core
 * @Author: Ice, Song
 * @CreateTime: 2025-04-16  20:03
 * @Description: BaseMessage
 * @Version: 1.0
 */
@Data
public class BaseMessage {
    /**
     * Message key
     */
    protected String key = UUID.randomUUID().toString();
    /**
     * Message source
     */
    protected String source;
    /**
     * Message send time
     */
    protected LocalDateTime sendTime = LocalDateTime.now();

}
