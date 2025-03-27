package com.songshilong.module.starter.common.utils;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.songshilong.module.starter.common.enums.ClientExceptionEnum;
import com.songshilong.module.starter.common.exception.ClientException;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.utils
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  14:25
 * @Description: BeanUtil
 * @Version: 1.0
 */
public class BeanUtil {

    /**
     * <a href="https://blog.csdn.net/Seky_fei/article/details/109960178">Jackson配置</a> <br>
     * 1. 反序列化的时候忽略未知的属性
     * 2. 关闭空对象不能序列化
     * 3. 关闭时间自动输出时间戳，并对时间格式化
     */
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    }


    /**
     * 创建目标对象，并将原对象的值拷贝到新的目标对象上。
     *
     * @param source 原对象
     * @param target 目标对象类型
     * @param <S>    原对象类型
     * @param <T>    目标对象类型
     * @return 拷贝结果
     */
    public static <S, T> T convert(S source, Class<T> target) {
        if (Objects.isNull(source)) {
            return null;
        }
        T targetInstance = null;
        try {
            targetInstance = target.newInstance();
            BeanUtils.copyProperties(source, targetInstance);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ClientException(e.getMessage(), 500);
        }
        return targetInstance;
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param obj 对象
     * @param <T> 对象类型
     * @return JSON字符串
     */
    public static <T> String toJSON(T obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        String json = null;
        try {
            json = MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ClientException(ClientExceptionEnum.JSON_SERIALIZE_FAIL);
        }
        return json;
    }

    /**
     * 将JSON字符串转换为对象
     * @param json JSON String
     * @param clazz 目标对象类型
     * @return T
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (StrUtil.isBlank(json)) {
            return null;
        }
        T result = null;
        try {
            result = MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new ClientException(ClientExceptionEnum.JSON_DESERIALIZE_FAIL);
        }
        return result;
    }


}
