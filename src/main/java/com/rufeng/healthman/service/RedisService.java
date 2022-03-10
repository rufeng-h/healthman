package com.rufeng.healthman.service;

/**
 * @author rufeng
 * @time 2022-03-09 23:26
 * @package com.rufeng.healthman.service
 * @description .
 */
public interface RedisService {
    boolean hasKey(String key);
    /**
     * 获取对象
     */
    <T> T getObject(Object key, Class<T> requeiedType);

    /**
     * 存储对象
     */
    void setObject(Object key, Object value);

    /**
     * 存储数据
     */
    void set(String key, String value);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 设置超期时间
     */
    void expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     *
     * @param delta 自增步长
     */
    Long increment(String key, long delta);
}
