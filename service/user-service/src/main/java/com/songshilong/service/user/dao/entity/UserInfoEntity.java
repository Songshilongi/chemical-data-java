package com.songshilong.service.user.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.songshilong.starter.database.base.BaseEntity;
import lombok.*;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.dao.entity
 * @Author: Shilong Song
 * @CreateTime: 2025-03-25  23:04
 * @Description: UserEntity
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("user_info")
public class UserInfoEntity extends BaseEntity {
    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户名/用户昵称
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String phone;
}
