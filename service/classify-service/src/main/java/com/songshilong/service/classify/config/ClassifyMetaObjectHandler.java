package com.songshilong.service.classify.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 分类服务的 MyBatis-Plus 字段自动填充处理器
 *
 * 注意：starter-database 中的同类 Bean 因实现存在缺陷（命名遮蔽 + 类型不匹配），
 * 实际不能正确填充字段。本类通过 @Primary 优先级覆盖，
 * 提供 createTime / updateTime / deleted 三个字段的自动填充。
 *
 * Bean 名为 classifyMetaObjectHandler，与 starter 的 myMetaObjectHandler 不冲突。
 */
@Slf4j
@Primary
@Component
public class ClassifyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
