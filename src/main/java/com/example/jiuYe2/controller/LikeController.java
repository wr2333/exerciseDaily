package com.example.jiuYe2.controller;

import com.example.jiuYe2.async.EventBase;
import com.example.jiuYe2.async.EventProducer;
import com.example.jiuYe2.async.EventType;
import com.example.jiuYe2.service.CommentService;
import com.example.jiuYe2.service.LikeService;
import com.example.jiuYe2.util.HostHolder;
import com.example.jiuYe2.util.JiuYeUtil;
import com.example.jiuYe2.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/like")
public class LikeController {

    @Resource
    LikeService likeService;

    @Resource
    CommentService commentService;

    @Resource
    HostHolder hostHolder;

    @Resource
    EventProducer eventProducer;

    @RequestMapping(path = {"/yes"}, method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam int commentId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.json2String(111, "未登录。");
        }
        long likeCount = likeService.like(hostHolder.getUser().getId(), JiuYeUtil.ENTITY_FLOOR, commentId);

        EventBase eventBase = new EventBase();
        eventBase.setEventType(EventType.LIKE);
        eventBase.setEntityType(JiuYeUtil.ENTITY_FLOOR);
        eventBase.setEntityId(commentId);
        eventBase.setFormId(hostHolder.getUser().getId());
        eventBase.setToId(commentService.getCommentById(commentId).getUserId());
        eventProducer.makeEvent(eventBase);

        return JsonUtil.json2String(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/no"}, method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam int commentId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.json2String(111, "未登录。");
        }
        long likeCount = likeService.dislike(hostHolder.getUser().getId(), JiuYeUtil.ENTITY_FLOOR, commentId);
        return JsonUtil.json2String(0, String.valueOf(likeCount));
    }

}
