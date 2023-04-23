package com.example.jiuYe2.async.handler;

import com.example.jiuYe2.async.EventBase;
import com.example.jiuYe2.async.EventType;

import java.util.List;

public interface EventHandler {

    void handle(EventBase eventBase);

    // 获取该处理器所有支持的事件类型
    List<EventType> getAllSupportedEventType();

}
