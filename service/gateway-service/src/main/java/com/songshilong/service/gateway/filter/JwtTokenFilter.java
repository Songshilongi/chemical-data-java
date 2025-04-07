package com.songshilong.service.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.module.starter.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.gateway.filter
 * @Author: Ice, Song
 * @CreateTime: 2025-04-03  11:03
 * @Description: JwtTokenFilter - token拦截器，用于拦截请求，并验证token
 * @Version: 1.0
 */
@Component
public class JwtTokenFilter extends AbstractGatewayFilterFactory<FilterConfig> {

    private static final String TOKEN_PREFIX = "Bearer ";


    public JwtTokenFilter() {
        super(FilterConfig.class);
    }

    @Override
    public GatewayFilter apply(FilterConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestPath = request.getPath().toString();
            if (this.isWhitePath(config.getWhitePathPre(), requestPath)) {
                return chain.filter(exchange);
            }
            if (this.isBlackPath(config.getBlackPathPre(), requestPath)) {
                String token = request.getHeaders().getFirst("Authorization");
                ServerHttpResponse response = exchange.getResponse();
                if (token == null || !token.startsWith(TOKEN_PREFIX)) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }

                Map<String, String> claims = JwtUtil.parseToken(token.replace(TOKEN_PREFIX, ""), config.getJwtSecret());
                if (MapUtil.isEmpty(claims)) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
                ServerHttpRequest.Builder builder = exchange.getRequest().mutate().headers(headers -> {
                    headers.set(Constant.USER_ID, claims.get(Constant.USER_ID));
                    headers.set(Constant.USERNAME, claims.get(Constant.USERNAME));
                    headers.set(Constant.EMAIL, claims.get(Constant.EMAIL));
                    headers.set(Constant.PHONE, claims.get(Constant.PHONE));
                });
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }
            return chain.filter(exchange);
        };
    }


    /**
     * 判断请求路径是否需要被拦截
     *
     * @param blackPathList 黑名单路径
     * @param requestPath   当前请求路径
     * @return true-需要被拦截，false-不需要被拦截
     */
    private Boolean isBlackPath(List<String> blackPathList, String requestPath) {
        if (CollUtil.isEmpty(blackPathList)) {
            return Boolean.FALSE;
        }
        return blackPathList.stream().anyMatch(requestPath::startsWith);
    }

    private Boolean isWhitePath(List<String> whitePathList, String requestPath) {
        if (CollUtil.isEmpty(whitePathList)) {
            return Boolean.FALSE;
        }
        return whitePathList.stream().anyMatch(requestPath::startsWith);
    }


}
