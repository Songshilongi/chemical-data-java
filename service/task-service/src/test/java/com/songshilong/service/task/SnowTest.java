package com.songshilong.service.task;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.alibaba.nacos.common.http.param.MediaType;
import com.songshilong.service.task.service.UploadService;
import com.songshilong.service.task.util.AliOssUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  22:17
 * @Description: SnowTest
 * @Version: 1.0
 */
@SpringBootTest
public class SnowTest {

    @Autowired
    private SnowflakeGenerator snowflakeGenerator;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private UploadService uploadService;


    @Test
    public void test() {
        System.out.println(snowflakeGenerator.next());
        System.out.println(snowflakeGenerator.next());
        System.out.println(snowflakeGenerator.next());
        System.out.println(snowflakeGenerator.next());
    }


    @Test
    public void cal() {
        String value = "upload.xlsx|dwgegrgegthgnhyjscdsfesfdfweafeae";
        System.out.println((value.hashCode() & 0x7FFFFFFF) & 15);
        String ossKey = String.format("temp/%s/%s.part", "songshilong", "key");
        System.out.println(ossKey);
    }

    @Test
    public void upload() throws IOException {
        String resourcePath = "template/template_text.xlsx";
        String path = "songshilong/xxfegegfgrg/11.part";
        Resource resource = new ClassPathResource(resourcePath);
        byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        MultipartFile file = new MockMultipartFile("file", "template_text.xlsx", MediaType.APPLICATION_OCTET_STREAM, bytes);
        System.out.println(aliOssUtil.uploadFile(file, path));


    }

    @Test
    public void exist() {
//        String path = "songshilong/xxfegegfgrg/1.part";
        String path = "songshilong/xxfegegfgrg/2.part";
        System.out.println(aliOssUtil.fileIsExist(path));
    }

    @Test
    public void testUpload() throws IOException {
        String resourcePath = "template/template_text.xlsx";
        Resource resource = new ClassPathResource(resourcePath);
        byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        MultipartFile file = new MockMultipartFile("file", "template_text.xlsx", MediaType.APPLICATION_OCTET_STREAM, bytes);

        uploadService.uploadChunk(file, "dwadwadwvrgrgr", 1, 2, "template_text.xlsx");
        uploadService.uploadChunk(file, "dxxxxxxxxwdwfr", 2, 2, "template_text.xlsx");
    }

    @Test
    public void testExist() {
//        System.out.println(uploadService.checkFile("dxxxxxxxxwdwfr", "template_text.xlsx"));
//        String fullPath = "resources/temp/" + "template_text.xlsx";
//        Path directory = Paths.get(fullPath).getParent();
//        System.out.println(directory);
        uploadService.mergeChunks("finger", 2, "template_text.xlsx");
    }




}
