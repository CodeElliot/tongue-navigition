package com.tongue.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * jwt验证
 */
public class JwtUtil {
    /**
     * 使用HS256算法生成jwt密钥
     * @param secretKey
     * @param ttlMillis
     * @param claim
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String,Object> claim){
        //1.指定签名时使用的算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //2.设置过期时间
        long ttl = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(ttl);

        //3.设置jwt的body
        JwtBuilder jwtBuilder = Jwts.builder()
                // 3.1如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claim)
                // 3.2设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                //3.3设置过期时间
                .setExpiration(exp);
        return jwtBuilder.compact();
    }

    /**
     * 解析token
     * @param secretKey
     * @param token
     * @return
     */
    public static Claims parseJWT(String secretKey,String token){
        Claims claims = Jwts.parser()
                //设置签名的密钥
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                //设置解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }
}
