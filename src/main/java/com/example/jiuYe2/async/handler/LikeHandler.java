package com.example.jiuYe2.async.handler;

import com.example.jiuYe2.async.EventBase;
import com.example.jiuYe2.async.EventType;
import com.example.jiuYe2.model.Message;
import com.example.jiuYe2.service.MessageService;
import com.example.jiuYe2.service.UserService;
import com.example.jiuYe2.util.JiuYeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {

    @Resource
    MessageService messageService;

    @Resource
    UserService userService;

    @Override
    public void handle(EventBase eventBase) {
        Message message = new Message();
        message.setFromId(JiuYeUtil.ADMINISTRATOR);
        message.setToId(eventBase.getToId());
        message.setContent("你的评论被用户" + userService.getUserById(eventBase.getFormId()).getName() + "赞了。");
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getAllSupportedEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
