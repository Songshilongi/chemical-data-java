package com.songshilong.service.task.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.properties
 * @Author: Ice, Song
 * @CreateTime: 2025-04-08  14:54
 * @Description: 阿里云OSS对象存储配置参数
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = AliYunOssProperty.PREFIX)
public class AliYunOssProperty {
    public static final String PREFIX = "chemical.property.task.oss";


    /**
     * OSS登录端点
     */
    private String endpoint;
    /**
     * Access Key
     */
    private String keyId;
    /**
     * Access Secret
     */
    private String keySecret;
    /**
     * Choose which bucket to use
     */
    private String bucketName;
    /**
     * bucket 地区
     */
    private String region;



}
