package com.example.jiuYe2.util;

// 方便获取redis中的元素键名
public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String LIKE_SET = "LIKE_SET";
    private static final String DISLIKE_SET = "DISLIKE_SET";
    private static final String EVENT_QUEUE = "EVENT_QUEUE";

    // 关注中
    private static final String CARE_Z_SET = "CARE_Z_SET";
    // 粉丝
    private static final String FANS_Z_SET = "FANS_Z_SET";

    private static final String TIMELINE_QUEUE = "TIMELINE_QUEUE";

    public static String getLikeSetKey(int entityType, int entityId) {
        return LIKE_SET + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDislikeSetKey(int entityType, int entityId) {
        return DISLIKE_SET + SPLIT + entityType + SPLIT + entityId;
    }

    // 用户关注的某个类型的对象集合，存储userId用户关注的entityType类型的所有对象。
    public static String getCareZSetKey(int userId, int entityType) {
        return CARE_Z_SET + SPLIT + userId + SPLIT + entityType;
    }

    public static String getEventQueueKey() {
        return EVENT_QUEUE;
    }

    // 关注某个实体的对象集合，存储关注entityType类型的entityId实体的所有对象。
    public static String getFansZSetKey(int entityType, int entityId) {
        return FANS_Z_SET + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getTimelineQueueKey(int userId) {
        return TIMELINE_QUEUE + SPLIT + userId;
    }

}
