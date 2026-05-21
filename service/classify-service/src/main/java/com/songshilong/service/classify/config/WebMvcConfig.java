package com.songshilong.service.classify.config;

import com.songshilong.service.classify.interceptor.TaskHeaderInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册自定义拦截器
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TaskHeaderInterceptor taskHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(taskHeaderInterceptor)
                .addPathPatterns("/api/**")          // 拦截所有业务接口
                .excludePathPatterns(                // 放行不需要用户身份的接口
                        "/doc.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/favicon.ico"
                );
    }
}
