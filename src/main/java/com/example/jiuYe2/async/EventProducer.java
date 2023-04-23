package com.example.jiuYe2.async;

import com.alibaba.fastjson.JSON;
import com.example.jiuYe2.util.JedisAdapter;
import com.example.jiuYe2.util.RedisKeyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EventProducer {

    @Resource
    JedisAdapter jedisAdapter;

    // 将实体化的事件json序列化，存入redis的事件队列中
    public boolean makeEvent(EventBase eventBase) {
        try {
            String eventJson = JSON.toJSONString(eventBase);
            String eventQueue = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(eventQueue, eventJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
