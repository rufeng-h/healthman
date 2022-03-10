package com.rufeng.healthman;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static com.rufeng.healthman.config.RedisConfig.REDIS_KEY_PREFIX;

@SpringBootTest
class HealthmanApplicationTests {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void clearAuthentication() {
        redisTemplate.delete("*" + REDIS_KEY_PREFIX + ":");
    }

}
