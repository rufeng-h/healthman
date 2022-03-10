package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author rufeng
 * @time 2022-03-09 23:26
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Service
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<Object, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisServiceImpl(RedisTemplate<Object, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    @SuppressWarnings("all")
    public <T> T getObject(Object key, Class<T> requeriedType) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setObject(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置过期时间，毫秒
     */
    @Override
    public void expire(String key, long expire) {
        stringRedisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }
}
