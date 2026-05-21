package com.songshilong.service.classify.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * RestTemplate 配置
 *
 * 重要参数：
 * - 连接池：最大 200 个连接，单 host 50 个
 * - 连接超时：5 秒（连不上 Python 立刻报错，而非永远等）
 * - 读取超时：60 秒（Python 处理超过 60 秒会被中断）
 * - 池获取超时：5 秒（池耗尽时不无限等）
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 连接池
     */
    @Bean
    public PoolingHttpClientConnectionManager connectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(200);
        manager.setDefaultMaxPerRoute(50);
        // 连接空闲超过 10 秒后再用前先验证一次（防止用到已断开的连接）
        manager.setValidateAfterInactivity(TimeValue.ofSeconds(10));
        return manager;
    }

    /**
     * HTTP 客户端
     */
    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager connectionManager) {
        // 所有超时配置统一放在 RequestConfig 里（适配 HttpClient 5.1.x）
        RequestConfig requestConfig = RequestConfig.custom()
                // 建立 TCP 连接的超时：5 秒
                .setConnectTimeout(Timeout.of(5, TimeUnit.SECONDS))
                // 等待响应的超时：60 秒（Python 处理时间通常 < 60 秒）
                .setResponseTimeout(Timeout.of(60, TimeUnit.SECONDS))
                // 从连接池获取连接的超时：5 秒
                .setConnectionRequestTimeout(Timeout.of(5, TimeUnit.SECONDS))
                .build();

        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .evictIdleConnections(TimeValue.ofMinutes(1))
                .build();
    }

    /**
     * RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}
