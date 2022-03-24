package com.rufeng.healthman;

import com.rufeng.healthman.config.support.ReturnMapPlugin;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import static com.rufeng.healthman.config.RedisConfig.REDIS_KEY_PREFIX;

@SpringBootTest
class HealthmanApplicationTests {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
    }

    @Test
    public void test(){
        ReturnMapPlugin bean = context.getBean(ReturnMapPlugin.class);
        System.out.println(bean);
    }

}
