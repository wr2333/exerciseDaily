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
        String likeSet = RedisKeyUtil.getLikeSetKey(entityType, entityId);
        jedisAdapter.sadd(likeSet, String.valueOf(userId));

        String dislikeSet = RedisKeyUtil.getDislikeSetKey(entityType, entityId);
        jedisAdapter.srem(dislikeSet, String.valueOf(userId));

        return jedisAdapter.scard(likeSet);
    }

    public long dislike(int userId, int entityType, int entityId) {
        // 将该用户加入dislike集合并移出like集合
        String dislikeSet = RedisKeyUtil.getDislikeSetKey(entityType, entityId);
        jedisAdapter.sadd(dislikeSet, String.valueOf(userId));

        String likeSet = RedisKeyUtil.getLikeSetKey(entityType, entityId);
        jedisAdapter.srem(likeSet, String.valueOf(userId));

        return jedisAdapter.scard(likeSet);
    }

    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeSet = RedisKeyUtil.getLikeSetKey(entityType, entityId);
        if (jedisAdapter.sismember(likeSet, String.valueOf(userId))) {
            return 1;
        }
        String dislikeSet = RedisKeyUtil.getDislikeSetKey(entityType, entityId);
        return jedisAdapter.sismember(dislikeSet, String.valueOf(userId)) ? -1 : 0;
    }

    public long getLikeCount(int entityType, int entityId) {
        String likeSet = RedisKeyUtil.getLikeSetKey(entityType, entityId);
        return jedisAdapter.scard(likeSet);
    }

}
