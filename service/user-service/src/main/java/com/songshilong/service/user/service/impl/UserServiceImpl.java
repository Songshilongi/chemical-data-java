package com.songshilong.service.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.module.starter.common.constant.RedisKeyConstant;
import com.songshilong.module.starter.common.enums.ClientExceptionEnum;
import com.songshilong.module.starter.common.enums.MailExceptionEnum;
import com.songshilong.module.starter.common.enums.UserExceptionEnum;
import com.songshilong.module.starter.common.exception.BusinessException;
import com.songshilong.module.starter.common.exception.ClientException;
import com.songshilong.module.starter.common.utils.BeanUtil;
import com.songshilong.module.starter.common.utils.JwtUtil;
import com.songshilong.module.starter.common.utils.Md5SecurityUtil;
import com.songshilong.service.user.dao.entity.UserInfoEntity;
import com.songshilong.service.user.dao.mapper.UserInfoMapper;
import com.songshilong.service.user.dto.request.PasswordMailResetRequest;
import com.songshilong.service.user.dto.request.ResetPasswordRequest;
import com.songshilong.service.user.dto.request.UserLoginRequest;
import com.songshilong.service.user.dto.request.UserRegisterRequest;
import com.songshilong.service.user.dto.response.PasswordMailResetResponse;
import com.songshilong.service.user.dto.response.UserLoginResponse;
import com.songshilong.service.user.dto.response.UserRegisterResponse;
import com.songshilong.service.user.properties.UserJwtProperty;
import com.songshilong.service.user.service.UserService;
import com.songshilong.service.user.util.UserUtil;
import com.songshilong.starter.cache.core.RedisUtil;
import com.songshilong.starter.mail.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
    private final UserJwtProperty userJwtProperty;
    private final RedissonClient redissonClient;
    private final RBloomFilter<String> usernameBloomFilter;
    private final RedisUtil redisUtil;
    private final EmailUtil emailUtil;

    private static final int LOGIN_TYPE_PASSWORD = 1;
    private static final int LOGIN_TYPE_EMAIL = 2;
    private static final int LOGIN_TYPE_PHONE = 3;

    private static final Integer USER_RESET_FREQUENCY = 1;


    @Override
    public Boolean resetPasswordByEmailVerifyCode(ResetPasswordRequest resetPasswordRequest) {
        String username = resetPasswordRequest.getUsername();
        String email = resetPasswordRequest.getEmail();
        String newPassword = resetPasswordRequest.getNewPassword();
        String verifyCode = resetPasswordRequest.getVerifyCode();
        UserInfoEntity userInfoEntity = this.findUserByUsernameAndEmail(username, email);
        if (Objects.isNull(userInfoEntity)) {
            throw new BusinessException(UserExceptionEnum.USER_NOT_EXIST);
        }
        String verifyCodeKey = RedisKeyConstant.USER_RESET_PASSWORD_EMAIL_PREFIX + username;
        String value = redisUtil.get(verifyCodeKey);
        if (StrUtil.isBlank(value)) {
            throw new BusinessException(MailExceptionEnum.VERIFY_CODE_EXPIRED);
        }
        if (!StrUtil.equals(verifyCode, value)) {
            throw new BusinessException(MailExceptionEnum.VERIFY_CODE_WRONG);
        }
        newPassword = Md5SecurityUtil.getMd5Value(newPassword);
        LambdaUpdateWrapper<UserInfoEntity> updateWrapper = Wrappers.lambdaUpdate(UserInfoEntity.class)
                .set(UserInfoEntity::getPassword, newPassword)
                .eq(UserInfoEntity::getUsername, username)
                .eq(UserInfoEntity::getEmail, email);
        int update = this.userInfoMapper.update(null, updateWrapper);
        if (update != 1) {
            throw new BusinessException(UserExceptionEnum.PASSWORD_UPDATE_FAIL);
        }
        return Boolean.TRUE;
    }

    private UserInfoEntity findUserByUsernameAndEmail(String username, String email) {
        LambdaQueryWrapper<UserInfoEntity> wrapper = Wrappers.lambdaQuery(UserInfoEntity.class)
                .select(UserInfoEntity::getId)
                .eq(UserInfoEntity::getUsername, username)
                .eq(UserInfoEntity::getEmail, email);
        return this.userInfoMapper.selectOne(wrapper);
    }

    @Override
    public PasswordMailResetResponse getPasswordMailResetCode(PasswordMailResetRequest passwordMailResetRequest) {
        String username = passwordMailResetRequest.getUsername();
        String email = passwordMailResetRequest.getEmail();
        // 发送频率限制
        String flag = RedisKeyConstant.USER_RESET_PASSWORD_FLAG_PREFIX + username;
        if (StrUtil.isNotBlank(redisUtil.get(flag))) {
            throw new BusinessException(MailExceptionEnum.TOO_FREQUENCY);
        }
        // 检查用户是否存在
        UserInfoEntity userInfoEntity = this.findUserByUsernameAndEmail(username, email);
        if (Objects.isNull(userInfoEntity)) {
            throw new BusinessException(UserExceptionEnum.USER_NOT_EXIST);
        }
        // 发送邮件
        String verifyCode = RandomUtil.randomString(10);
        redisUtil.set(RedisKeyConstant.USER_RESET_PASSWORD_EMAIL_PREFIX + username, verifyCode, 5, TimeUnit.MINUTES);
        redisUtil.set(flag, verifyCode, USER_RESET_FREQUENCY, TimeUnit.MINUTES);
        emailUtil.send(email, "Chemical-Data—Platform", this.buildEmailContent(verifyCode));
        return BeanUtil.convert(passwordMailResetRequest, PasswordMailResetResponse.class);
    }

    private String buildEmailContent(String verifyCode) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><head><title>多模态</title></head><body>");
        builder.append("您好<br/>");
        builder.append("您的验证码是：").append(verifyCode).append("<br/>");
        builder.append("您可以复制此验证码并返回至多模态化学信息抽取平台系统找回密码页面，以验证您的邮箱。<br/>");
        builder.append("此验证码只能使用一次，在5分钟内有效。验证成功则自动失效。<br/>");
        builder.append("如果您没有进行上述操作，请忽略此邮件。");
        return builder.toString();
    }

    @Override
    public Boolean hasUsername(String username) {
        boolean contains = usernameBloomFilter.contains(username);
        if (contains) {
            return redisUtil.setIsMember(RedisKeyConstant.USER_REGISTER_USERNAME + UserUtil.hashShardingIndex(username), username);
        }
        return Boolean.FALSE;
    }

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        if (Objects.isNull(userRegisterRequest)) {
            return null;
        }
        String username = userRegisterRequest.getUsername();
        if (this.hasUsername(username)) {
            throw new BusinessException(UserExceptionEnum.USER_EXIST);
        }

        RLock lock = redissonClient.getLock(RedisKeyConstant.USER_REGISTER_USERNAME_LOCK_PREFIX + username);
        if (!lock.tryLock()) {
            throw new ClientException(ClientExceptionEnum.REGISTER_GET_LOCK_FAIL);
        }
        try {
            UserInfoEntity userInfoEntity = BeanUtil.convert(userRegisterRequest, UserInfoEntity.class);
            if (Objects.isNull(userInfoEntity)) {
                throw new BusinessException(UserExceptionEnum.USER_REGISTER_FAIL);
            }
            userInfoEntity.setPassword(Md5SecurityUtil.getMd5Value(userInfoEntity.getPassword()));
            try {
                int insert = userInfoMapper.insert(userInfoEntity);
                if (insert != 1) {
                    throw new BusinessException(UserExceptionEnum.USER_REGISTER_FAIL);
                }
            } catch (DuplicateKeyException exception) {
                throw new BusinessException(UserExceptionEnum.USER_EXIST);
            }
            usernameBloomFilter.add(username);
            redisUtil.setAdd(RedisKeyConstant.USER_REGISTER_USERNAME + UserUtil.hashShardingIndex(username), username);
        } finally {
            lock.unlock();
        }
        return BeanUtil.convert(userRegisterRequest, UserRegisterResponse.class);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Integer loginType = userLoginRequest.getLoginType();
        UserLoginResponse userLoginResponse = null;
        switch (loginType) {
            case LOGIN_TYPE_PASSWORD:
                userLoginResponse = this.userPasswordLogin(userLoginRequest);
                break;
            case LOGIN_TYPE_EMAIL:
                userLoginResponse = this.userEmailLogin(userLoginRequest);
                break;
            case LOGIN_TYPE_PHONE:
                userLoginResponse = this.userPhoneLogin(userLoginRequest);
                break;
            default:
                throw new BusinessException(UserExceptionEnum.USER_LOGIN_FAIL_UNKNOWN_TYPE);
        }
        return userLoginResponse;
    }

    /**
     * 手机号验证码登录
     *
     * @param userLoginRequest {@link UserLoginRequest}
     * @return {@link UserLoginResponse}
     */
    private UserLoginResponse userPhoneLogin(UserLoginRequest userLoginRequest) {
        return null;
    }

    /**
     * 邮箱验证码登录
     *
     * @param userLoginRequest {@link UserLoginRequest}
     * @return {@link UserLoginResponse}
     */
    private UserLoginResponse userEmailLogin(UserLoginRequest userLoginRequest) {
        return null;
    }

    /**
     * 密码匹配登录
     *
     * @param userLoginRequest {@link UserLoginRequest}
     * @return {@link UserLoginResponse}
     */
    private UserLoginResponse userPasswordLogin(UserLoginRequest userLoginRequest) {
        String password = userLoginRequest.getPassword();
        String username = userLoginRequest.getUsername();
        if (StrUtil.isBlank(password) || StrUtil.isBlank(username)) {
            throw new BusinessException(UserExceptionEnum.USER_LOGIN_FAIL_NONE_PASSWORD);
        }
        String md5Password = Md5SecurityUtil.getMd5Value(password);
        LambdaQueryWrapper<UserInfoEntity> queryWrapper = Wrappers.lambdaQuery(UserInfoEntity.class)
                .eq(UserInfoEntity::getUsername, username)
                .eq(UserInfoEntity::getPassword, md5Password);
        UserLoginResponse userLoginResponse = Optional.ofNullable(this.userInfoMapper.selectOne(queryWrapper))
                .map(item -> {
                    UserLoginResponse convert = BeanUtil.convert(item, UserLoginResponse.class);
                    convert.setUserId(item.getId());
                    return convert;
                })
                .orElseThrow(() -> new BusinessException(UserExceptionEnum.USER_LOGIN_FAIL));
        userLoginResponse.setToken(this.getUserToken(userLoginResponse));
        return userLoginResponse;
    }

    private String getUserToken(UserLoginResponse userLoginResponse) {
        Map<String, String> claims = new HashMap<>();
        claims.put(Constant.USER_ID, String.valueOf(userLoginResponse.getUserId()));
        claims.put(Constant.USERNAME, userLoginResponse.getUsername());
        claims.put(Constant.EMAIL, userLoginResponse.getEmail());
        claims.put(Constant.PHONE, userLoginResponse.getPhone());
        return JwtUtil.generateToken(userJwtProperty.getSecretKey(), userJwtProperty.getTtlMillis(), claims);
    }
}
