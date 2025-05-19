package com.songshilong.service.task.service.impl;

import cn.hutool.core.util.StrUtil;
import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.module.starter.common.constant.RedisKeyConstant;
import com.songshilong.module.starter.common.enums.ClientExceptionEnum;
import com.songshilong.module.starter.common.exception.ClientException;
import com.songshilong.module.starter.common.utils.BeanUtil;
import com.songshilong.service.task.context.BaseContext;
import com.songshilong.service.task.dto.PartUploadCacheParam;
import com.songshilong.service.task.dto.response.MergeChunkResponse;
import com.songshilong.service.task.service.UploadService;
import com.songshilong.service.task.util.AliOssUtil;
import com.songshilong.starter.cache.core.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.service.impl
 * @Author: Ice, Song
 * @CreateTime: 2025-05-18  18:50
 * @Description: UploadServiceImpl
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {


    private final AliOssUtil aliOssUtil;
    private final RedisUtil redisUtil;


    private static final String TEMPLATE_TEXT_RESOURCE_PATH = "template/template_text.xlsx";
    private static final Resource TEXT_TEMPLATE_RESOURCE;

    static {
        TEXT_TEMPLATE_RESOURCE = new ClassPathResource(TEMPLATE_TEXT_RESOURCE_PATH);
    }

    @Override
    public ResponseEntity<Resource> getTextTemplate() {
        if (!TEXT_TEMPLATE_RESOURCE.exists()) {
            return ResponseEntity.notFound().build();
        }
        ResponseEntity<Resource> result = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode("文本抽取批量处理模板.xlsx", "UTF-8"));
            result = ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(TEXT_TEMPLATE_RESOURCE);
        } catch (IOException e) {
            throw new ClientException(ClientExceptionEnum.TEMPLATE_RESOURCE_LOAD_FAIL);
        }
        return result;
    }


    @Override
    public Boolean checkFile(String fileKey, String filename) {
        String key = RedisKeyConstant.CHUNK_UPLOAD_PREFIX + calculateSlot(filename) + ":" + BaseContext.getContext(Constant.USERNAME) + ":" + filename + ':' + fileKey;
        String target = redisUtil.get(key);
        return StrUtil.isNotBlank(target);
    }

    /**
     * ossPath: username/fileKey/chunk.part
     * 过期时间设置为1h，防止数据堆积
     */
    @Override
    public Boolean uploadChunk(MultipartFile file, String fileKey, Integer chunk, Integer total, String filename) {
        try {
            String ossPath = String.format("chunks/%s/%s/%d.part", BaseContext.getContext(Constant.USERNAME), fileKey, chunk);
//            String resourceOssPath = aliOssUtil.uploadFile(file, ossPath);
            String resourceOssPath = saveFile(file, ossPath);
            PartUploadCacheParam params = PartUploadCacheParam
                    .builder()
                    .index(chunk)
                    .total(total)
                    .path(resourceOssPath)
                    .build();
            String key = RedisKeyConstant.CHUNK_UPLOAD_PREFIX + calculateSlot(filename) + ":" + BaseContext.getContext(Constant.USERNAME) + ":" + filename + ':' + fileKey;
            redisUtil.set(key, BeanUtil.toJSON(params), 24, TimeUnit.HOURS);
        } catch (Exception e) {
            throw new ClientException(ClientExceptionEnum.CHUNK_UPLOAD_FAIL);
        }
        return Boolean.TRUE;
    }

    @Override
    public MergeChunkResponse mergeChunks(String fileKey, Integer total, String filename) {
        String key = RedisKeyConstant.CHUNK_UPLOAD_PREFIX + calculateSlot(filename) + ":" + BaseContext.getContext(Constant.USERNAME) + ":" + filename + ':';
        List<String> chunkDataList = redisUtil.getAllFromNameSpace(key);
        List<String> chunkPathList = new ArrayList<>();
        List<Integer> ChunkIndexList = new ArrayList<>();
        chunkDataList.forEach(item -> {
            PartUploadCacheParam params = BeanUtil.toObject(item, PartUploadCacheParam.class);
            if (!Objects.isNull(params)) {
                chunkPathList.add(params.getPath());
                ChunkIndexList.add(params.getIndex());
            }
        });
        if (total == chunkPathList.size()) {
            String path = this.mergeChunks(fileKey, filename, chunkPathList);
            return MergeChunkResponse.builder().chunkPathList(chunkPathList).missingChunkIndexList(null).build();
        }
        List<Integer> missingChunkIndexList = new ArrayList<>();
        for (int i = 1; i <= total; i++) {
            if (!ChunkIndexList.contains(i)) {
                missingChunkIndexList.add(i);
            }
        }
        return MergeChunkResponse.builder().chunkPathList(chunkPathList).missingChunkIndexList(missingChunkIndexList).build();
    }

    /**
     * 计算已经上传的文件key的Redis Set分片位置
     * hash % 16 = hash & (16 - 1)
     * 当分片数 n 是 2 的幂时，hash % n 等价于 hash & (n-1)。
     *
     * @param value 需要被计算的value
     * @return 位置
     */
    private int calculateSlot(String value) {
        return (value.hashCode() & 0x7FFFFFFF) & 15;
    }


    private String mergeChunks(String fileKey, String filename, List<String> partValues) {
        String uploadDir = "file/upload/" + BaseContext.getContext(Constant.USERNAME);
        Path targetDir = Paths.get(uploadDir);
        Path targetPath = targetDir.resolve(fileKey + "-" + filename);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            // 创建上传目录（如果不存在）
            Files.createDirectories(targetDir);

            fos = new FileOutputStream(targetPath.toFile(), true);
            bos = new BufferedOutputStream(fos);

            for (String chunkPathStr : partValues) {
                Path chunkFilePath = Paths.get(chunkPathStr);

                if (!Files.exists(chunkFilePath)) {
                    throw new ClientException(ClientExceptionEnum.CHUNK_MISSING);
                }

                byte[] buffer = Files.readAllBytes(chunkFilePath);
                bos.write(buffer);
            }
            bos.flush();
            return targetPath.toString();
        } catch (IOException e) {
            // 尝试删除已创建的目标文件
            try {
                Files.deleteIfExists(targetPath);
            } catch (IOException ex) {
                throw new ClientException(ClientExceptionEnum.CHUNK_MERGED_FAIL);
            }
            throw new ClientException(ClientExceptionEnum.CHUNK_MERGED_FAIL);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    throw new ClientException(ClientExceptionEnum.CHUNK_MERGED_FAIL);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new ClientException(ClientExceptionEnum.CHUNK_MERGED_FAIL);
                }
            }
        }
    }


    /**
     * 保存分片文件，按道理OSS应该是一个单独的服务，然后给前端端提供上传分片文件的接口，然后前端再合并分片文件，但是这个服务目前用来学习，所以就直接写在这里了。
     */
    private String saveFile(MultipartFile file, String path) {
        String fullPath = "file/temp/" + path;
        Path targetPath = Paths.get(fullPath);
        Path directory = targetPath.getParent();
        try {
            Files.createDirectories(directory);
            File tempFile = File.createTempFile("upload-", ".tmp");
            file.transferTo(tempFile);
            Files.move(tempFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ClientException(ClientExceptionEnum.CHUNK_UPLOAD_FAIL);
        }
        return fullPath;
    }
}
