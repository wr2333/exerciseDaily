package com.example.jiuYe2.service;

import com.example.jiuYe2.model.User;
import com.example.jiuYe2.util.JedisAdapter;
import com.example.jiuYe2.util.JiuYeUtil;
import com.example.jiuYe2.util.RedisKeyUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {

    @Resource
    JedisAdapter jedisAdapter;

    public boolean follow(int userId, int entityType, int entityId) {
        String careZSet = RedisKeyUtil.getCareZSetKey(userId, entityType);
        String fansZSet = RedisKeyUtil.getFansZSetKey(entityType, entityId);

        Jedis jedis = jedisAdapter.getJedis();
        // 事务完成原子操作
        Transaction transaction = jedis.multi();
        // 在用户关注集合中加入该实体
        transaction.zadd(careZSet, new Date().getTime(), String.valueOf(entityId));
        // 在实体粉丝集合中加入该用户
        transaction.zadd(fansZSet, new Date().getTime(), String.valueOf(userId));
        List<Object> result = jedisAdapter.exec(transaction, jedis);
        return result.size() == 2 && (long) result.get(0) > 0 && (long) result.get(1) > 0;
    }

    public boolean unfollow(int userId, int entityType, int entityId) {
        String careZSet = RedisKeyUtil.getCareZSetKey(userId, entityType);
        String fansZSet = RedisKeyUtil.getFansZSetKey(entityType, entityId);

        Jedis jedis = jedisAdapter.getJedis();
        Transaction transaction = jedis.multi();
        // 在用户关注集合中删除该实体
        transaction.zrem(careZSet, String.valueOf(entityId));
        // 在实体粉丝集合中删除该用户
        transaction.zrem(fansZSet, String.valueOf(userId));
        List<Object> result = jedisAdapter.exec(transaction, jedis);
        return result.size() == 2 && (long) result.get(0) > 0 && (long) result.get(1) > 0;
    }

    public List<Integer> getFansIds(int entityType, int entityId, int count) {
        String fansZSet = RedisKeyUtil.getFansZSetKey(entityType, entityId);
        Set<String> set = jedisAdapter.zrange(fansZSet, 0, count);
        List<Integer> fansIds = new ArrayList<>();
        for (String id : set) {
            fansIds.add(Integer.valueOf(id));
        }
        return fansIds;
    }

    public List<Integer> getCareIds(int userId, int count) {
        String careZSet = RedisKeyUtil.getCareZSetKey(userId, JiuYeUtil.ENTITY_USER);
        Set<String> set = jedisAdapter.zrange(careZSet, 0, count);
        List<Integer> careIds = new ArrayList<>();
        for (String id : set) {
            careIds.add(Integer.valueOf(id));
        }
        return careIds;
    }

    public boolean isFans(int userId, int entityType, int entityId) {
        String fansZSet = RedisKeyUtil.getFansZSetKey(entityType, entityId);
        return jedisAdapter.zscore(fansZSet, String.valueOf(userId)) != null ;
    }


}
