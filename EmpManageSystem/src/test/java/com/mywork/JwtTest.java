package com.mywork;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 生成测试类
 */
public class JwtTest {

    // 测试生成 JWT Token
    @Test
    public void genToken() {
        // 1. 准备自定义声明（存放用户信息）
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", "1");       // 用户ID
        claims.put("username", "GTP"); // 用户名

        // 2. 生成 Token
        String token = JWT.create()
                // 添加自定义声明（键为"user"，值为存放用户信息的map）
                .withClaim("user", claims)
                // 设置过期时间（当前时间 + 3小时）
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                // 使用 HMAC256 算法签名，密钥为"itheima"
                .sign(Algorithm.HMAC256("itheima"));

        // 3. 打印生成的 Token
        System.out.println("生成的JWT Token：" + token);
    }
    @Test
    public void testParse() {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9"+
                ".eyJleHAiOjE3NjE5NjMwMzcsInVzZXIiOnsiaWQiOiIxIiwidXNlcm5hbWUiOiJHVFAifX0"+
                ".7OL_-BJcJd20RHm3L18sXrGw5B3E5e0snzbV4S9E6-U";
        JWTVerifier jwtVerifier= JWT.require(Algorithm.HMAC256("itheima")).build();

        jwtVerifier.verify(token);
        DecodedJWT decodedJWT = jwtVerifier.verify(token);//验证token，生成一个解析后的JWT对象
        Map<String , Claim>  claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}