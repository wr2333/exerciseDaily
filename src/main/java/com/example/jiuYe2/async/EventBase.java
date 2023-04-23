package com.example.jiuYe2.async;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class EventBase {

    private EventType eventType;
    // 触发事件的用户
    private int formId;
    // 被影响的用户
    private int toId;
    // 事件涉及的实体
    private int entityType;
    private int entityId;

    private final Map<String, String> extra = new HashMap<>();

    public void setExtraElem(String key, String value) {
        this.extra.put(key, value);
    }

    public String getExtraElem(String key) {
        return this.extra.get(key);
    }

}
