// 验证码Redis工具类
package com.rabbiter.hospital.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class CaptchaRedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 存储验证码，有效期5分钟
    public void setCaptcha(String key, String code) {
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
    }

    // 获取验证码
    public String getCaptcha(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除验证码
    public void deleteCaptcha(String key) {
        redisTemplate.delete(key);
    }
}