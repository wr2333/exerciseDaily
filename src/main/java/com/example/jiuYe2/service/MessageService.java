package com.example.jiuYe2.service;

import com.example.jiuYe2.dao.MessageDAO;
import com.example.jiuYe2.model.Message;
import com.example.jiuYe2.util.HealthUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService {

    @Resource
    MessageDAO messageDAO;

    @Resource
    HealthUtil healthUtil;

    public int addMessage(Message message) {
        message.setContent(healthUtil.filter(message.getContent()));
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }

}
