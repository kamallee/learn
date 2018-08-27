package com.crazyfish.kamal.dao.impl;

import com.crazyfish.kamal.dao.context.IRedisDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * Created by kamal on 15/8/3.
 */
@Repository("redisDataSource")
public class RedisDataSourceImpl implements IRedisDataSource {
    @Autowired
    private ShardedJedisPool shardedJedisPool;
    public ShardedJedis getRedisClient() {
        try{
            ShardedJedis shardedJedis  = shardedJedisPool.getResource();
            return shardedJedis;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedisPool.returnResource(shardedJedis);
    }
}
