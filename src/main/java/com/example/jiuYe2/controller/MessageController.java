package com.example.jiuYe2.controller;

import com.example.jiuYe2.model.Message;
import com.example.jiuYe2.service.MessageService;
import com.example.jiuYe2.service.UserService;
import com.example.jiuYe2.util.HostHolder;
import com.example.jiuYe2.util.JsonUtil;
import com.example.jiuYe2.util.ViewObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Resource
    MessageService messageService;

    @Resource
    UserService userService;

    @Resource
    HostHolder hostHolder;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam int toId, @RequestParam String content) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.json2String(111, "用户未登录。");
        }
        Message message = new Message();
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(toId);
        message.setContent(content);
        if (messageService.addMessage(message) > 0) {
            return JsonUtil.json2String(0);
        }
        return JsonUtil.json2String(1, "消息发送失败。");
    }

    @RequestMapping("/list")
    @ResponseBody
    public String getConversationList() {
        if (hostHolder.getUser() == null) {
            return JsonUtil.json2String(111, "用户未登录");
        }
        int selfId = hostHolder.getUser().getId();
        List<Message> messages = messageService.getConversationList(selfId, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Message message : messages) {
            ViewObject vo = new ViewObject();
            vo.put("conversation", message);
            int friendId = selfId == message.getFromId() ? message.getToId() : message.getFromId();
            vo.put("user", userService.getUserById(friendId));
            vo.put("unreadCount", messageService.getConversationUnreadCount(selfId, message.getConversationId()));
            vos.add(vo);
        }
        StringBuilder sb = new StringBuilder();
        for (ViewObject temp : vos) {
            sb.append(temp.vo2String()).append("\n");
        }
        return sb.toString();
    }

    @RequestMapping("/detail")
    @ResponseBody
    public String getConversation(@RequestParam String conversationId) {
        List<Message> messages = messageService.getConversationDetail(conversationId, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Message message : messages) {
            ViewObject vo = new ViewObject();
            vo.put("message", message);
            vo.put("user", userService.getUserById(message.getFromId()));
            vos.add(vo);
        }
        StringBuilder sb = new StringBuilder();
        for (ViewObject temp : vos) {
            sb.append(temp.vo2String()).append("\n");
        }
        return sb.toString();
    }

}
