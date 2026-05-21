

package com.songshilong.module.starter.common.idempotent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 幂等注解，防止用户重复提交表单信息
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoDuplicateSubmit {

    /**
     * 触发幂等失败逻辑时，返回的错误提示信息
     */
    // 默认方法定义
    String message() default "您操作太快，请稍后再试";
}
