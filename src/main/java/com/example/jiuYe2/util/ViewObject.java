package com.example.jiuYe2.util;

import java.util.HashMap;
import java.util.Map;

// 用于给前端返回视图数据。
public class ViewObject {
    private final Map<String, Object> map = new HashMap<>();

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public String vo2String() {
        StringBuilder sb = new StringBuilder("[ ");
        map.forEach((key, value) -> {
            sb.append(key).append(": ").append(value).append(" | ");
        });
        return sb.append(" ]").toString();
    }

}
