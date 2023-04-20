package com.example.jiuYe2.util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    public static String json2String(int code, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        if (msg != null) {
            jsonObject.put("msg", msg);
        }
        return jsonObject.toJSONString();
    }

    public static String json2String(int code) {
        return json2String(code, null);
    }

}
