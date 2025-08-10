package com.rabbiter.hospital.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JwtUtil {
    private static final String SignKey = "1HU&**UUY**(GNH";
    /**
     * 生成token
     */
    public static String getToken(Map<String, String> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 30);             //设置过期时间为30天

        //创建jwt builder
        final JWTCreator.Builder builder = JWT.create();
        //JWT Payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        return builder.withExpiresAt(instance.getTime())//指定令牌过期时间
                .sign(Algorithm.HMAC256(SignKey));//sign
    }

    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SignKey)).build().verify(token);
    }
}
