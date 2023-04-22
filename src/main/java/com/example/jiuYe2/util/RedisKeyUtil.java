package com.example.jiuYe2.util;

public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String LIKE = "LIKE";
    private static final String DISLIKE = "DISLIKE";

    public static String getLikeKey(int entityType, int entityId) {
        return LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDislikeKey(int entityType, int entityId) {
        return DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }

}
