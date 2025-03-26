package com.songshilong.starter.database.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.songshilong.module.starter.common.enums.DataValidEnum;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.database.handler
 * @Author: Shilong Song
 * @CreateTime: 2025-03-26  14:36
 * @Description: MyMetaObjectHandler-自定义插入的逻辑
 * @Version: 1.0
 */
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "deleted", Integer.class, DataValidEnum.VALID.code());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
