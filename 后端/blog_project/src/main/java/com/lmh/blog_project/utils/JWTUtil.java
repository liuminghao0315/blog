package com.lmh.blog_project.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JWTUtil {

    @Value("${token.secretKey}")
    private String secretKey = "miyaomiyaomiyao"; // 与生成 token 时使用的密钥相同

    // 验证 Token 是否有效，返回布尔值
    public boolean validateToken(String token, Map<String, String> map) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);  // 如果验证失败会抛出异常
            Claim userId = decodedJWT.getClaim("userId");
            Claim userRole = decodedJWT.getClaim("userRole");
            Claim timeStamp = decodedJWT.getClaim("timeStamp");
            map.put("userId", userId.asString());
            map.put("userRole", userRole.asString());
            map.put("timeStamp", timeStamp.asLong().toString());
            return true;  // Token 验证成功
        } catch (Exception e) {
            // 捕获所有异常并返回 false
            System.out.println("Token verification failed: " + e.getMessage());
            return false;
        }
    }
}