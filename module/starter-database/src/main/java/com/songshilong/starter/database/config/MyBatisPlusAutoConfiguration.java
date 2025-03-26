package com.songshilong.starter.database.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.songshilong.starter.database.handler.MyMetaObjectHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.database.config
 * @Author: Shilong Song
 * @CreateTime: 2025-03-26  14:33
 * @Description: MyBatisPlusAutoConfiguration
 * @Version: 1.0
 */
@AutoConfiguration
public class MyBatisPlusAutoConfiguration {


    /**
     * MybatisPlus-分页插件
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


    /**
     * 自定义插入和更新数据的元数据逻辑
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    @ConditionalOnMissingBean
    public MyMetaObjectHandler myMetaObjectHandler(){
        return new MyMetaObjectHandler();
    }


}
