package com.springcloud.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springcloud.backend.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单的 JWT 工具，提供 token 生成与校验。
 */
public class JWTUtil {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);
    /**
     * 密钥
     */
    private static final String SECRET = "45459";

    /**
     * 过期时间（秒）
     */
    private static final long EXPIRATION = 86400L;

    /**
     * 生成用户 token
     */
    public static String createToken(User user) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create()
                .withHeader(map)
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole())
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 校验 token 并解析内容。
     */
    public static Map<String, Claim> verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e) {
            logger.error("token解码异常", e);
            return null;
        }
    }
}
