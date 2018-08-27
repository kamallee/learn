package com.crazyfish.kamal.dao.context;

import redis.clients.jedis.ShardedJedis;

/**
 * Created by kamal on 15/8/3.
 */
public interface IRedisDataSource {
    ShardedJedis getRedisClient();
    void returnResource(ShardedJedis shardedJedis);
}
