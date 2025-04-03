package com.songshilong.service.task.interceptor;

import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.service.task.context.BaseContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task.interceptor
 * @Author: Ice, Song
 * @CreateTime: 2025-04-03  14:08
 * @Description: TaskHeaderInterceptor
 * @Version: 1.0
 */
@Component
public class TaskHeaderInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        BaseContext.setContext(Constant.USER_ID, request.getHeader(Constant.USER_ID));
        BaseContext.setContext(Constant.USERNAME, request.getHeader(Constant.USERNAME));
        BaseContext.setContext(Constant.EMAIL, request.getHeader(Constant.EMAIL));
        BaseContext.setContext(Constant.PHONE, request.getHeader(Constant.PHONE));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.clearContext();
    }
}
