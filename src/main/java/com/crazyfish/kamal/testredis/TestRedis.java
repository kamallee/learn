package com.crazyfish.kamal.testredis;

import redis.clients.jedis.*;

/**
 * Created by kamal on 15/7/21.
 */
public class TestRedis {
    private Jedis jedis;
    private JedisPool jedisPool;
    private ShardedJedis shardedJedis;
    private ShardedJedisPool shardedJedisPool;
    public TestRedis(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(5);
        poolConfig.setMaxActive(20);
        poolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool("127.0.0.1",6379);
        jedis = jedisPool.getResource();
    }
    public void someAction(){
        jedis.set("one","hello");
        jedis.set("two","word");
        System.out.println("one:" + jedis.get("one") + ":" + jedis.get("two"));
        jedisPool.returnResource(jedis);
    }

    public static void main(String args[]){
        TestRedis redisTest = new TestRedis();
        redisTest.someAction();
    }
}
