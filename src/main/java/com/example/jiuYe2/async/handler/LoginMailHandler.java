package com.example.jiuYe2.async.handler;

import com.example.jiuYe2.async.EventBase;
import com.example.jiuYe2.async.EventType;
import com.example.jiuYe2.util.MailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class LoginMailHandler implements EventHandler {

    @Resource
    MailSender mailSender;

    @Override
    public void handle(EventBase eventBase) {
//        mailSender.send(eventBase.getExtraElem("email"), "欢迎登录");
        System.out.println("已发送邮件");
    }

    @Override
    public List<EventType> getAllSupportedEventType() {
        return Arrays.asList(EventType.LOGIN);
    }
}
