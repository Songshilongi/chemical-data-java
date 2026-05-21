package com.songshilong.service.classify.interceptor;

import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.service.classify.context.BaseContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TaskHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String userId = request.getHeader(Constant.USER_ID);
        String username = request.getHeader(Constant.USERNAME);
        String email = request.getHeader(Constant.EMAIL);
        String phone = request.getHeader(Constant.PHONE);

        log.info("【拦截器】请求 URI = {}, USER_ID = {}, USERNAME = {}",
                request.getRequestURI(), userId, username);

        BaseContext.setContext(Constant.USER_ID, userId);
        BaseContext.setContext(Constant.USERNAME, username);
        BaseContext.setContext(Constant.EMAIL, email);
        BaseContext.setContext(Constant.PHONE, phone);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.clearContext();
    }
}
