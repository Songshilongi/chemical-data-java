package com.songshilong.service.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.properties
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  17:48
 * @Description: UsernameBloomFilterProperty
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = UsernameBloomFilterProperty.PREFIX)
public class UsernameBloomFilterProperty {
    public static final String PREFIX = "chemical.property.bloom.filter.username";

    /**
     * 布隆过滤器的名字
     */
    private String name;
    /**
     * 每个元素预期的插入数量
     */
    private Long expectedInterceptors;
    /**
     *误判率
     */
    private Double falseProbability;

}
