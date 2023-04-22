package com.example.jiuYe2.model;

import java.util.Date;

import lombok.Data;

@Data
public class Message {

    private Integer id;
    private Integer fromId;
    private Integer toId;
    private String content;
    private Integer hasRead;
    private String conversationId;
    private Date createdDate;

    // mybatis会在操作数据库时调用getter，若可在getter内计算出该字段值，则可以不赋值。
    public String getConversationId() {
        if (fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        } else {
            return String.format("%d_%d", toId, fromId);
        }
    }

    // 使用@Data注解后，在getter，setter内调用类变量会导致toString出问题，需要重写toString。
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", content='" + content + '\'' +
                ", hasRead=" + hasRead +
                ", conversationId='" + conversationId + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}