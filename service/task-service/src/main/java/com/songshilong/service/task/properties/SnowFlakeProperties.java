package com.songshilong.service.task.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.properties
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  22:09
 * @Description: SnowFlakeProperties
 * @Version: 1.0
 */
@ConfigurationProperties(prefix = SnowFlakeProperties.PREFIX)
@Data
public class SnowFlakeProperties {
    public static final String PREFIX = "chemical.property.snow";

    private Integer workerId;
    private Integer dataCenterId;
}
