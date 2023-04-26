package com.example.jiuYe2.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.jiuYe2.async.EventBase;
import com.example.jiuYe2.async.EventType;
import com.example.jiuYe2.model.Comment;
import com.example.jiuYe2.model.Feed;
import com.example.jiuYe2.model.User;
import com.example.jiuYe2.service.CommentService;
import com.example.jiuYe2.service.FeedService;
import com.example.jiuYe2.service.FollowService;
import com.example.jiuYe2.service.UserService;
import com.example.jiuYe2.util.JedisAdapter;
import com.example.jiuYe2.util.JiuYeUtil;
import com.example.jiuYe2.util.RedisKeyUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class FeedHandler implements EventHandler {

    @Resource
    UserService userService;

    @Resource
    CommentService commentService;

    @Resource
    FeedService feedService;

    @Resource
    FollowService followService;

    @Resource
    JedisAdapter jedisAdapter;

    private String data2String(EventBase eventBase) {
        User user = userService.getUserById(eventBase.getFromId());
        if (user != null) {
            Map<String, String> data = new HashMap<>();
            data.put("userId", String.valueOf(user.getId()));
            data.put("userName", user.getName());
            if (eventBase.getEventType() == EventType.COMMENT || (eventBase.getEventType() == EventType.FOLLOW && eventBase.getEntityType() == JiuYeUtil.ENTITY_FLOOR)) {
                Comment comment = commentService.getCommentById(eventBase.getEntityId());
                data.put("commentId", String.valueOf(comment.getId()));
                data.put("content", String.valueOf(comment.getContent()));
            }
            return JSONObject.toJSONString(data);
        }
        return null;
    }

    @Override
    public void handle(EventBase eventBase) {
        Feed feed = new Feed();
        feed.setUserId(eventBase.getFromId());
        feed.setType(eventBase.getEventType().getValue());
        // 数据库里只存关键信息的json串
        feed.setData(data2String(eventBase));
        // 存入数据库，用于拉
        feedService.addFeed(feed);

        // 存入redis，用于推。数据库和redis应同步。
        List<Integer> fansIds = followService.getFansIds(JiuYeUtil.ENTITY_USER, eventBase.getFromId(), Integer.MAX_VALUE);
        // 游客动态列表
        fansIds.add(0);
        for (int fansId : fansIds) {
            String timelineQueue = RedisKeyUtil.getTimelineQueueKey(fansId);
            // 将动态的id存入每个粉丝的动态列表
            jedisAdapter.lpush(timelineQueue, String.valueOf(feed.getId()));
        }

    }

    @Override
    public List<EventType> getAllSupportedEventType() {
        return Arrays.asList(EventType.COMMENT, EventType.FOLLOW);
    }
}
