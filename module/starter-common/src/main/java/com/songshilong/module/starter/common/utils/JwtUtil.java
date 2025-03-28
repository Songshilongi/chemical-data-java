package com.songshilong.module.starter.common.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.songshilong.module.starter.common.constant.Constant;
import com.songshilong.module.starter.common.enums.SecurityExceptionEnum;
import com.songshilong.module.starter.common.exception.ClientException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.utils
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  15:32
 * @Description: JwtUtil
 * @Version: 1.0
 */
public class JwtUtil {


    /**
     * 通过JWT生成token
     *
     * @param secretKey 密钥
     * @param ttlMillis 过期时间
     * @param claims    负载
     * @return token-JWT
     */
    public static String generateToken(String secretKey, long ttlMillis, Map<String, String> claims) {
        if (MapUtil.isEmpty(claims)) {
            return null;
        }
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + ttlMillis);
        return JWT.create()
                .withAudience("chemical-platform")
                .withIssuedAt(new Date(now))
                .withExpiresAt(expiration)
                .withClaim(Constant.USERNAME, claims.get(Constant.USERNAME))
                .withClaim(Constant.EMAIL, claims.get(Constant.EMAIL))
                .withClaim(Constant.PHONE, claims.get(Constant.PHONE))
                .sign(Algorithm.HMAC384(secretKey));
    }

    /**
     * 解析token获取负载
     *
     * @param token     token
     * @param secretKey 密钥
     * @return payload
     */
    public static Map<String, String> parseToken(String token, String secretKey) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC384(secretKey)).build().verify(token);
            result.put(Constant.USERNAME, verify.getClaim(Constant.USERNAME).asString());
            result.put(Constant.EMAIL, verify.getClaim(Constant.EMAIL).asString());
            result.put(Constant.PHONE, verify.getClaim(Constant.PHONE).asString());

        } catch (TokenExpiredException e) {
            throw new ClientException(SecurityExceptionEnum.TOKEN_EXPIRATION);
        } catch (SignatureVerificationException e) {
            throw new ClientException(SecurityExceptionEnum.INVALID_SIGNATURE);
        } catch (Exception e) {
            throw new ClientException(SecurityExceptionEnum.FAIL_PARSE_JWT);
        }
        return result;
    }


}
