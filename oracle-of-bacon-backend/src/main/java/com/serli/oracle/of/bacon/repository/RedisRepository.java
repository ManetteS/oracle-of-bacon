package com.serli.oracle.of.bacon.repository;

import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisRepository {
    private final Jedis jedis;

    public RedisRepository() {
        this.jedis = new Jedis("localhost");
    }

    public void addLastReseach(String last) {
        jedis.lpush("lastTen", last);
        if (jedis.llen("lastTen") > 10) {
            jedis.rpop("lastTen");
        }
    }

    public List<String> getLastTenSearches() {        
        return jedis.lrange("lastTen", 0, 10);
    }
}
