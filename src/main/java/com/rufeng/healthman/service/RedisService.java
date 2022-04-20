package com.rufeng.healthman.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.rufeng.healthman.config.RedisConfig.REDIS_KEY_PREFIX;

/**
 * @author rufeng
 * @time 2022-03-09 23:26
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Service
public class RedisService {
    private final RedisTemplate<Object, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisService(RedisTemplate<Object, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private String genRealKey(String key) {
        return REDIS_KEY_PREFIX + ":" + key;
    }


    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(genRealKey(key)));
    }


    @SuppressWarnings("all")
    public <T> T getObject(String key, Class<T> requeriedType) {
        return (T) redisTemplate.opsForValue().get(genRealKey(key));
    }


    public void setObject(String key, Object value) {
        redisTemplate.opsForValue().set(genRealKey(key), value);
    }


    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(genRealKey(key), value);
    }


    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(genRealKey(key));
    }

    /**
     * 设置过期时间，毫秒
     */

    public void expire(String key, long expire) {
        stringRedisTemplate.expire(genRealKey(key), expire, TimeUnit.MILLISECONDS);
    }


    /**
     * 删除对象
     */
    public void removeObject(String key) {
        redisTemplate.delete(genRealKey(key));
    }

    /**
     * 删除字符串
     */
    public void remove(String key) {
        stringRedisTemplate.delete(genRealKey(key));
    }


    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(genRealKey(key), delta);
    }
}
