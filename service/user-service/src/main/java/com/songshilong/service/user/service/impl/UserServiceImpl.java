package com.songshilong.service.user.service.impl;

import com.songshilong.module.starter.common.enums.UserExceptionEnum;
import com.songshilong.module.starter.common.exception.BusinessException;
import com.songshilong.module.starter.common.utils.BeanUtil;
import com.songshilong.module.starter.common.utils.Md5SecurityUtil;
import com.songshilong.service.user.dao.entity.UserInfoEntity;
import com.songshilong.service.user.dao.mapper.UserInfoMapper;
import com.songshilong.service.user.dto.request.UserRegisterRequest;
import com.songshilong.service.user.dto.response.UserRegisterResponse;
import com.songshilong.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.user.service.impl
 * @Author: Ice, Song
 * @CreateTime: 2025-03-26  20:29
 * @Description: UserServiceImpl
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userInfoMapper;


    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        if (Objects.isNull(userRegisterRequest)) {
            return null;
        }
        UserInfoEntity userInfoEntity = BeanUtil.convert(userRegisterRequest, UserInfoEntity.class);
        if (Objects.isNull(userInfoEntity)) {
            throw new BusinessException(UserExceptionEnum.USER_REGISTER_FAIL);
        }
        userInfoEntity.setPassword(Md5SecurityUtil.getMd5ValueWithSalt(userInfoEntity.getPassword()));
        try {
            int insert = userInfoMapper.insert(userInfoEntity);
            if (insert != 1) {
                throw new BusinessException(UserExceptionEnum.USER_REGISTER_FAIL);
            }
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(UserExceptionEnum.USER_EXIST);
        }
        return BeanUtil.convert(userRegisterRequest, UserRegisterResponse.class);
    }
}
