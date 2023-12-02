package com.example.redis.jedis;

import com.example.redis.util.JedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @author Lucas (Weiye) Wang
 * @version 1.0.0
 * @date 28/11/2023 - 11:48 pm
 * @Description
 */
public class SecKill {

    /**
     *
     * @param prodid
     * @param uid
     * @version 1.0
     */
    public void doSecKill(String prodid, String uid) {
        // 1. 检查是否为空
        if (prodid == null || uid == null) {
            return;
        }
        // 2. 连接redis
        Jedis jedis = new Jedis("192.168.241.150", 6379);

        // 3. 拼接key
        String kcKey = "sk:" + prodid + ":qt";
        String userKey = "sk:" + prodid + ":user";

        // 4.秒杀是否开始
        if (jedis.get(kcKey) == null) {
            System.out.println("秒杀尚未开始");
            jedis.close();
            return;
        }

        // 5. 判断是否已经抢购过
        if (jedis.sismember(userKey, uid)) {
            System.out.println("已经抢购过了");
            jedis.close();
            return;
        }

        // 6. 判断是否还有库存
        String qtStr = jedis.get(kcKey);
        if (Integer.parseInt(qtStr) < 1) {
            System.out.println("已经抢购完了");
            jedis.close();
            return;
        }

        // 7. 秒杀
        // 7.1 减库存
        jedis.decr(kcKey);
        // 7.2 加清单
        jedis.sadd(userKey, uid);
        System.out.println("秒杀成功");
        jedis.close();
    }

    public static Boolean doSecKillAdvanced(String prodid, String uid) {
        //1 uid和prodid非空判断
        if(uid == null || prodid == null) {
            return false;
        }

        //2 连接redis
        //Jedis jedis = new Jedis("192.168.44.168",6379);
        //通过连接池得到jedis对象
        JedisPool jedisPoolInstance = JedisPoolUtil.getJedisPoolInstance();
        Jedis jedis = jedisPoolInstance.getResource();

        //3 拼接key
        // 3.1 库存key
        String kcKey = "sk:"+prodid+":qt";
        // 3.2 秒杀成功用户key
        String userKey = "sk:"+prodid+":user";

        //监视库存, 使用乐观锁
        jedis.watch(kcKey);

        //这些逻辑不包含于事务中
        //4 获取库存，如果库存null，秒杀还没有开始
        String kc = jedis.get(kcKey);
        if(kc == null) {
            System.out.println("秒杀还没有开始，请等待");
            jedis.close();
            return false;
        }

        // 5 判断用户是否重复秒杀操作
        if(jedis.sismember(userKey, uid)) {
            System.out.println("已经秒杀成功了，不能重复秒杀");
            jedis.close();
            return false;
        }

        //6 判断如果商品数量，库存数量小于1，秒杀结束
        if(Integer.parseInt(kc)<=0) {
            System.out.println("秒杀已经结束了");
            jedis.close();
            return false;
        }

        //7 秒杀过程
        //使用事务
        Transaction multi = jedis.multi();

        //组队操作
        multi.decr(kcKey);
        multi.sadd(userKey,uid);

        //执行
        List<Object> results = multi.exec();

        if(results == null || results.size()==0) {
            System.out.println("秒杀失败了....");
            jedis.close();
            return false;
        }

        //7.1 库存-1
        //jedis.decr(kcKey);
        //7.2 把秒杀成功用户添加清单里面
        //jedis.sadd(userKey,uid);

        System.out.println("秒杀成功了..");
        jedis.close();
        return true;
    }

}
