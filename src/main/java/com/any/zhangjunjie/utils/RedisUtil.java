package com.any.zhangjunjie.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(128);   // 最大连接数
        config.setMaxIdle(32);     // 最大空闲连接
        config.setMinIdle(8);      // 最小空闲连接

        pool = new JedisPool(config, "localhost", 6379, 2000); // 2000ms超时
    }

    public static Jedis getConnect() {
        return pool.getResource();
    }
}
