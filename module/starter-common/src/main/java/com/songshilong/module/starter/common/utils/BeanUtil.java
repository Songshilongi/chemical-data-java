package com.songshilong.module.starter.common.utils;

import com.songshilong.module.starter.common.exception.ClientException;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

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
}
