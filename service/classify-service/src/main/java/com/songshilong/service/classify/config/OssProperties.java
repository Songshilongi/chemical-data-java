package com.songshilong.service.classify.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云 OSS 配置属性
 * 自动绑定 application.yaml 中 aliyun.oss.* 下的所有配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    /**
     * OSS 服务接入域名
     */
    private String endpoint;

    /**
     * 访问密钥 ID
     */
    private String accessKeyId;

    /**
     * 访问密钥 Secret
     */
    private String accessKeySecret;

    /**
     * Bucket 名称
     */
    private String bucketName;

    /**
     * 签名 URL 过期时间（秒）
     */
    private Integer urlExpireSeconds = 3600;
}
