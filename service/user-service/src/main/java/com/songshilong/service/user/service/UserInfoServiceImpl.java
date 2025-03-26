package com.songshilong.service.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songshilong.service.user.dao.entity.UserInfoEntity;
import com.songshilong.service.user.dao.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.service
 * @Author: Shilong Song
 * @CreateTime: 2025-03-25  23:11
 * @Description: UserInfoServiceImpl
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements UserInfoService {


    @Override
    public void hello() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUsername("Ice,Song");
        this.save(userInfoEntity);
        List<UserInfoEntity> list = this.lambdaQuery().select().list();
        list.forEach(System.out::println);
        System.out.println("hello");
    }
}
