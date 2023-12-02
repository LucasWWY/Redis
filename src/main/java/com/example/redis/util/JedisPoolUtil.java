package com.example.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Lucas (Weiye) Wang
 * @version 1.0.0
 * @date 29/11/2023 - 12:53 am
 * @Description
 */
public class JedisPoolUtil {

    // 它的主要作用是确保变量的可见性和部分有序性，但它不保证操作的原子性
    /*
    可见性：
    当一个变量被声明为 volatile 时，对这个变量的写操作会立即刷新到主内存中，而读操作会直接从主内存中读取。
    这确保了在不同线程中读写同一个 volatile 变量时，一个线程写入的值对其他线程是立即可见的。

    禁止指令重排序：
    volatile 变量可以防止指令重排序优化。这意味着在 volatile 变量的读写操作之前的代码不会被重排序到读写操作之后。
    这有助于维护多线程下的内存一致性和执行顺序。

    不保证原子性：
    尽管 volatile 提供了操作的可见性保证，但它并不保证操作的原子性。
    例如，递增操作（如 volatileVar++）并不是一个原子操作，因此即使变量是 volatile 的，这样的操作也可能不安全。
    使用场景：

    volatile 适用于那些只由一个线程写，但可能由多个线程读的变量。
    对于更复杂的多线程操作，例如计数或集合操作，应该使用更强的同步机制，如 synchronized 或 java.util.concurrent 包中的工具。
     */

    private static volatile JedisPool jedisPool = null;

    private JedisPoolUtil() {
    }

    public static JedisPool getJedisPoolInstance() {
        if (null == jedisPool) {
            synchronized (JedisPoolUtil.class) {
                if (null == jedisPool) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(200);
                    poolConfig.setMaxIdle(32);
                    poolConfig.setMaxWaitMillis(100*1000);
                    poolConfig.setBlockWhenExhausted(true);
                    poolConfig.setTestOnBorrow(true);  // ping  PONG

                    jedisPool = new JedisPool(poolConfig, "", 6379, 60000 );
                }
            }
        }
        return jedisPool;
    }

    public static void release(JedisPool jedisPool, Jedis jedis) {
        if (null != jedis) {
            jedisPool.returnResource(jedis);
        }
    }

}
