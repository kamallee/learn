package com.crazyfish.kamal.dao;

import com.crazyfish.kamal.dao.context.IRedisDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;

/**
 * Created by kamal on 15/8/3.
 */
public class TestJedisContext implements InitializingBean{
    @Autowired
    private IRedisDataSource redisDataSource;
    public void afterPropertiesSet() throws Exception {
        //ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        //shardedJedis.set("app","123yyyy");
        //System.out.println("TestJedisContext" + shardedJedis.get("app"));
        //redisDataSource.returnResource(shardedJedis);
    }
}
