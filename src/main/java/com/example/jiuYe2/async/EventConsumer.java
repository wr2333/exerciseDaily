package com.example.jiuYe2.async;

import com.alibaba.fastjson.JSON;
import com.example.jiuYe2.async.handler.EventHandler;
import com.example.jiuYe2.util.JedisAdapter;
import com.example.jiuYe2.util.RedisKeyUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    @Resource
    JedisAdapter jedisAdapter;

    // 事件匹配表，根据事件类型匹配对应的处理类
    private final Map<EventType, List<EventHandler>> matcher = new HashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取当前程序上下文
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 程序启动后，根据程序上下文在程序中找出所有处理类。
        Map<String, EventHandler> handlers = applicationContext.getBeansOfType(EventHandler.class);
        // 遍历所有处理类，获取每个处理类支持的事件类型，并在匹配表中将该处理类匹配到这些事件类型。
        handlers.forEach((name, handler) -> {
            List<EventType> eventTypes = handler.getAllSupportedEventType();
            for (EventType eventType : eventTypes) {
                if (!matcher.containsKey(eventType)) {
                    matcher.put(eventType, new ArrayList<>());
                }
                matcher.get(eventType).add(handler);
            }
        });

        new Thread(() -> {
            String eventQueue = RedisKeyUtil.getEventQueueKey();
            List<String> events;
            EventBase eventBase;
            while (true) {
                // 始终尝试获取redis中被序列化的的事件队列，获取不到就阻塞
                events = jedisAdapter.brpop(0, eventQueue);
                for (String event : events) {
                    // 跳过开头的队列名
                    if (event.equals(eventQueue)) {
                        continue;
                    }
                    // 将该事件从字符串中反序列化出来
                    eventBase = JSON.parseObject(event, EventBase.class);
                    // 获取解决该事件需要的所有处理器，将该事件交给这些处理器处理。
                    for (EventHandler handler : matcher.get(eventBase.getEventType())) {
                        handler.handle(eventBase);
                    }
                }
            }
        }).start();
    }
}





