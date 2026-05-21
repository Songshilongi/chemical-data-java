package com.songshilong.service.classify.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.songshilong.service.classify.config.OssProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassifyUpload {

    private final OssProperties ossProperties;

    private OSS ossClient;

    /**
     * Bean 初始化后执行，构建 OSSClient
     * 使用 @PostConstruct 而不是构造方法，是为了等 ossProperties 注入完成后再初始化
     */
    @PostConstruct
    public void init() {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setMaxConnections(200);
        conf.setSocketTimeout(50000);
        conf.setConnectionTimeout(50000);
        conf.setConnectionRequestTimeout(1000);
        conf.setIdleConnectionTime(30000);

        this.ossClient = new OSSClientBuilder().build(
                ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                conf
        );
        log.info("OSS 客户端初始化完成，endpoint={}, bucket={}",
                ossProperties.getEndpoint(), ossProperties.getBucketName());
    }

    /**
     * 上传文件到 OSS，返回文件 URL 和文件名
     */
    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown";

        // ★★★ 关键改进：用文件内容 MD5 作为 Key，相同内容的文件复用同一个 OSS Object ★★★
        byte[] fileBytes = file.getBytes();
        String fileHash = DigestUtils.md5DigestAsHex(fileBytes);
        String fileName = fileHash + "-" + originalFilename;
        String bucketName = ossProperties.getBucketName();

        // 检查 OSS 上是否已存在（秒传逻辑）
        if (ossClient.doesObjectExist(bucketName, fileName)) {
            log.info("文件已存在于 OSS，无需重新上传: {}", fileName);
        } else {
            try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
                ossClient.putObject(putObjectRequest);
            }
            log.info("文件上传成功: {}", fileName);
        }

        // 生成签名 URL
        Date expiration = new Date(System.currentTimeMillis() + ossProperties.getUrlExpireSeconds() * 1000L);
        URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
        String fileUrl = url.toString();

        Map<String, String> result = new HashMap<>();
        result.put("fileUrl", fileUrl);
        result.put("fileName", fileName);
        return result;
    }

    @PreDestroy
    public void shutdown() {
        if (this.ossClient != null) {
            this.ossClient.shutdown();
            log.info("OSS 客户端已关闭");
        }
    }
}
