package com.example.jiuYe2.service;

import com.example.jiuYe2.util.JedisAdapter;
import com.example.jiuYe2.util.RedisKeyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeService {

    @Resource
    JedisAdapter jedisAdapter;

    public long like(int userId, int entityType, int entityId) {
        // 将该用户加入like集合并移出dislike集合
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdapter.srem(dislikeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entityType, int entityId) {
        // 将该用户加入dislike集合并移出like集合
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdapter.sadd(dislikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(likeKey);
    }

}
