package com.songshilong.service.task.util;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.songshilong.module.starter.common.enums.TaskTypeEnum;
import com.songshilong.service.task.properties.AliYunOssProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.util
 * @Author: Ice, Song
 * @CreateTime: 2025-04-08  15:23
 * @Description: AliOssUtil
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class AliOssUtil {

    private final AliYunOssProperty aliYunOssProperty;

    private static final String SMILES_IMAGE_PATH = "smiles/";
    private static final String Reaction_IMAGE_PATH = "reaction/";
    private static final String REAXYS_PDF_PATH = "reaxys/";
    private static final String HTTPS = "https://";

    /**
     * 构建阿里云OSS客户端
     *
     * @return {@link OSS} 阿里云客户端
     */
    private OSS buildClient() {
        DefaultCredentialProvider defaultCredentialProvider =
                CredentialsProviderFactory.newDefaultCredentialProvider(aliYunOssProperty.getKeyId(),
                        aliYunOssProperty.getKeySecret());

        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

        return OSSClientBuilder.create()
                .endpoint(aliYunOssProperty.getEndpoint())
                .credentialsProvider(defaultCredentialProvider)
                .region(aliYunOssProperty.getRegion())
                .build();
    }

    /**
     * 判断文件是否存在
     *
     * @param path 目标完整路径 /xx/yy/resource.xxx
     * @return true-已经存在
     */
    public Boolean fileIsExist(String path) {
        OSS ossClient = null;
        Boolean result = Boolean.FALSE;
        try {
            ossClient = this.buildClient();
            result = ossClient.doesObjectExist(aliYunOssProperty.getBucketName(), path);
        } finally {
            if (!Objects.isNull(ossClient)) {
                ossClient.shutdown();
            }
        }
        return result;
    }

    /**
     * 上传单个文件
     *
     * @param file 文件资源
     * @param path 目标完整路径 /xx/yy/resource.xxx
     * @return 完整的请求路径
     */
    public String uploadFile(MultipartFile file, String path) {
        OSS ossClient = null;
        String url = null;
        try {
            ossClient = this.buildClient();
            ossClient.putObject(aliYunOssProperty.getBucketName(), path, file.getInputStream());
            url = HTTPS + aliYunOssProperty.getBucketName() + "." + aliYunOssProperty.getEndpoint() + "/" + path;
        } catch (OSSException | ClientException | IOException e) {
            throw new RuntimeException("文件上传失败");
        } finally {
            if (!Objects.isNull(ossClient)) {
                ossClient.shutdown();
            }
        }
        return url;
    }

    /**
     * 上传单个文件
     *
     * @param file     单个文件
     * @param taskType 任务类型
     * @return OSS访问地址
     */
    public String uploadFile(MultipartFile file, TaskTypeEnum taskType) {
        OSS ossClient = null;
        String url = null;
        try {
            ossClient = this.buildClient();
            String fileName = Optional.ofNullable(this.buildFileName(file, taskType))
                    .orElseThrow(() -> new RuntimeException("文件上传失败"));
            ossClient.putObject(aliYunOssProperty.getBucketName(), fileName, file.getInputStream());
            url = HTTPS + aliYunOssProperty.getBucketName() + "." + aliYunOssProperty.getEndpoint() + "/" + fileName;
        } catch (IOException | ClientException | OSSException e) {
            throw new RuntimeException("文件上传失败");
        } finally {
            if (!Objects.isNull(ossClient)) {
                ossClient.shutdown();
            }
        }
        return url;
    }


    /**
     * 根据任务类型构建文件名
     *
     * @param file     需要被上传的文件
     * @param taskType 任务类型
     * @return 文件路径名
     */
    private String buildFileName(MultipartFile file, TaskTypeEnum taskType) {
        String fileName = null;
        switch (taskType) {
            case SMILES:
                fileName = SMILES_IMAGE_PATH + UUID.randomUUID() + '-' + file.getOriginalFilename();
                break;
            case REACTION:
                fileName = Reaction_IMAGE_PATH + UUID.randomUUID() + '-' + file.getOriginalFilename();
                break;
            case REAXYS:
                fileName = REAXYS_PDF_PATH + UUID.randomUUID() + '-' + file.getOriginalFilename();
                break;
            default:
                break;
        }
        return fileName;
    }


}


