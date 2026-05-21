package com.songshilong.service.task.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
@Service
public class ClassifyUpload {
    // 阿里云OSS的配置信息（先写死）
    private static final String endpoint = "oss-cn-shanghai.aliyuncs.com";
    private static final String accessKeyId = "LTAI5t9hiVbM4x2vrWE4izRL";
    private static final String accessKeySecret = "LkiLSFepKPjepXOlGMqSK1Z70VyQAT";
    private static final String bucketName = "predictmodel";

    private OSS ossClient;

    public ClassifyUpload() {
        // 初始化OSSClient
        this.ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    // 上传文件的方法，返回文件URL
    public String uploadFile(MultipartFile file) throws IOException {
        // 生成唯一的文件名
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        // 创建一个PutObjectRequest，指定上传到的Bucket，文件名和文件流
        InputStream inputStream = file.getInputStream();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);

        // 上传文件到OSS
        ossClient.putObject(putObjectRequest);

        // 生成文件的公开URL
        URL url = ossClient.generatePresignedUrl(bucketName, fileName, null);
        String fileUrl = url.toString();

        // 关闭OSSClient
        ossClient.shutdown();

        // 返回文件URL
        return fileUrl;
    }
}
