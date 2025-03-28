CREATE TABLE `user_info`
(
    `id`          int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `username`    varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci  DEFAULT NULL COMMENT '用户名/用户昵称',
    `password`    varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户密码',
    `email`       varchar(30) COLLATE utf8mb3_unicode_ci                        DEFAULT NULL COMMENT '用户邮箱',
    `phone`       varchar(11) COLLATE utf8mb3_unicode_ci                        DEFAULT NULL COMMENT '用户手机号',
    `create_time` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(255) COLLATE utf8mb3_unicode_ci                       DEFAULT NULL COMMENT '创建人',
    `update_time` datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `update_by`   varchar(255) COLLATE utf8mb3_unicode_ci                       DEFAULT NULL COMMENT '更新人',
    `deleted`     int                                                           DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
