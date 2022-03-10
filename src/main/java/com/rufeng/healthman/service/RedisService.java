package com.rufeng.healthman.service;

/**
 * @author rufeng
 * @time 2022-03-09 23:26
 * @package com.rufeng.healthman.service
 * @description .
 */
public interface RedisService {
    /**
     * .
     *
     * @param key key
     * @return bool
     */
    boolean hasKey(String key);

    /**
     * 获取对象
     *
     * @param key          the key
     * @param requeiedType 目标对象类型
     * @return 目标对象
     */
    <T> T getObject(Object key, Class<T> requeiedType);

    /**
     * 存储对象
     *
     * @param key   k
     * @param value v
     */
    void setObject(Object key, Object value);

    /**
     * 存储字符串
     *
     * @param key   k
     * @param value v
     */
    void set(String key, String value);

    /**
     * 获取字符串
     */
    String get(String key);

    /**
     * 设置超期时间，毫秒
     *
     * @param key    k
     * @param expire 过期时间，毫秒
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
