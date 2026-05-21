package com.songshilong.module.starter.common.config;

import com.songshilong.module.starter.common.idempotent.NoDuplicateSubmitAspect;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;

public class IdempotentConfiguration {
    /**
     * 防止用户重复提交表单信息切面控制器
     */
    @Bean
    public NoDuplicateSubmitAspect noDuplicateSubmitAspect(RedissonClient redissonClient) {
        return new NoDuplicateSubmitAspect(redissonClient);
    }

}
