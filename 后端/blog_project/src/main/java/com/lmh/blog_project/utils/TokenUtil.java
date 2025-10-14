package com.lmh.blog_project.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lmh.blog_project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    @Value("${token.secretKey}")
    private String secretKey = "miyaomiyaomiyao";

    @Autowired
    private JWTUtil jwtUtil = new JWTUtil();
    /**
     * 加密token.
     */
    public String getToken(User user) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 执行时间的格式化处理，得到期望格式的时间字符串
        String userGmtCreated = df.format(user.getGmtCreated());
        String userGmtModified = df.format(user.getGmtModified());
        //这个是放到负载payLoad 里面,魔法值可以使用常量类进行封装.
        String token = JWT
                .create()
                .withClaim("userId" ,user.getId())
                .withClaim("userRole" ,user.getAuthority().toString())
                .withClaim("timeStamp", System.currentTimeMillis())
                .sign(Algorithm.HMAC256(secretKey));
        return token;
    }
    /**
     * 解析token.
     * {
     * "userId": "weizhong",
     * "userRole": "ROLE_ADMIN",
     * "timeStamp": "134143214"
     * }
     */
    public Map<String, String> parseToken(String token) {
        Map<String, String> map = new HashMap<String, String>();
        if(jwtUtil.validateToken(token, map)) {
            return map;
        }else{
            return null;
        }
    }
}
