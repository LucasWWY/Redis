package com.example.redis.jedis;

/**
 * @author Lucas (Weiye) Wang
 * @version 1.0.0
 * @date 30/11/2023 - 8:44 pm
 * @Description
 */

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 演示redis集群操作
 */
public class RedisCluster {

    public static void main(String[] args) {
        //创建对象
        HostAndPort hostAndPort = new HostAndPort("192.168.44.168", 6379); // 任何节点都可以访问整个集群
        JedisCluster jedisCluster = new JedisCluster(hostAndPort);

        //进行操作
        jedisCluster.set("b1","value1");

        String value = jedisCluster.get("b1");
        System.out.println("value: "+value);

        jedisCluster.close();
    }
}
