package com.songshilong.service.user.serialize;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.serialize
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  14:56
 * @Description: 手机序列化脱敏
 * @Version: 1.0
 */
public class PhoneDesensitizationSerializer extends JsonSerializer<String> {

    /**
     * Deserialize
     * @param s phone number
     */
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String newPhoneNumber = DesensitizedUtil.mobilePhone(s);
        jsonGenerator.writeString(newPhoneNumber);
    }
}
