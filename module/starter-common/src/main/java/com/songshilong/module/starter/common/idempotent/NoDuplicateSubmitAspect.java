
package com.songshilong.module.starter.common.idempotent;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;

import com.songshilong.module.starter.common.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 防止用户重复提交表单信息切面控制器
 */
@Aspect
@RequiredArgsConstructor
public final class NoDuplicateSubmitAspect {

    private final RedissonClient redissonClient;
    private static final Logger log = LoggerFactory.getLogger(NoDuplicateSubmitAspect.class);

    /**
     * 增强方法标记 {@link NoDuplicateSubmit} 注解逻辑
     */

    //拦截所有标记了 @NoDuplicateSubmit 注解的方法
    @Around("@annotation(com.songshilong.module.starter.common.idempotent.NoDuplicateSubmit)")
    public Object noDuplicateSubmit(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("--- [防重提交切面] 开始执行 ---");
        // 获取目标方法上的 @NoDuplicateSubmit 注解，包含了默认的错误信息等属性。
        NoDuplicateSubmit noDuplicateSubmit = getNoDuplicateSubmitAnnotation(joinPoint);
        // 获取分布式锁标识
        String lockKey = String.format("no-duplicate-submit:path:%s:currentUserId:%s:md5:%s", getServletPath(), getCurrentUserId(), calcArgsMD5(joinPoint));
        //使用 RedissonClient 获取一个分布式锁对象 RLock，锁的标识符就是上面构建的 lockKey。

        log.info("[防重提交切面] 生成的 Lock Key: {}", lockKey);
        RLock lock = redissonClient.getLock(lockKey);
        // 尝试获取锁，获取锁失败就意味着已经重复提交，直接抛出异常
        log.info("[防重提交切面] 尝试获取锁...");
        if (!lock.tryLock()) {
            throw new ClientException(noDuplicateSubmit.message());
        }
        log.info("[防重提交切面] 成功获取锁。Key: {}", lockKey);
        try {
            return joinPoint.proceed();
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("[防重提交切面] 释放锁。Key: {}", lockKey);
            }
        }
    }

    /**
     * @return 返回自定义防重复提交注解
     */
    public static NoDuplicateSubmit getNoDuplicateSubmitAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
        return targetMethod.getAnnotation(NoDuplicateSubmit.class);
    }

    /**
     * @return 获取当前线程上下文 ServletPath
     */
    private String getServletPath() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return sra.getRequest().getServletPath();
    }

    /**
     * @return 当前操作用户 ID
     */
    private String getCurrentUserId() {
        // 这里先通过模拟的形式代替。
        return "1810518709471555585";
    }

    /**
     * @return joinPoint md5
     */
    private String calcArgsMD5(ProceedingJoinPoint joinPoint) {
        String argsStr = Arrays.stream(joinPoint.getArgs())
                .map(this::getArgString)
                .collect(Collectors.joining(" "));
        return DigestUtil.md5Hex(argsStr);
    }

    private String getArgString(Object arg) {
        if (arg == null) {
            return StrUtil.EMPTY;
        }
        if (arg instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) arg;
            return file.getOriginalFilename() + "_" + file.getSize();
        }
        return JSON.toJSONString(arg);
    }
}
