package com.example.jiuYe2.controller;

import com.example.jiuYe2.model.User;
import com.example.jiuYe2.service.FollowService;
import com.example.jiuYe2.service.UserService;
import com.example.jiuYe2.util.HostHolder;
import com.example.jiuYe2.util.JiuYeUtil;
import com.example.jiuYe2.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @Resource
    FollowService followService;

    @Resource
    UserService userService;

    @Resource
    HostHolder hostHolder;

    @RequestMapping("/user/yes")
    @ResponseBody
    public String followUser(@RequestParam int userId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.json2String(111, "用户未登录");
        }
        User self = hostHolder.getUser();
        if (followService.follow(self.getId(), JiuYeUtil.ENTITY_USER, userId)) {
            return JsonUtil.json2String(0);
        }
        return JsonUtil.json2String(1, "关注用户失败。");
    }

    @RequestMapping("/user/no")
    @ResponseBody
    public String unfollowUser(@RequestParam int userId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.json2String(111, "用户未登录");
        }
        User self = hostHolder.getUser();
        if (followService.unfollow(self.getId(), JiuYeUtil.ENTITY_USER, userId)) {
            return JsonUtil.json2String(0);
        }
        return JsonUtil.json2String(1, "取消关注用户失败。");
    }

    @RequestMapping("/comment/yes")
    @ResponseBody
    public String followComment(@RequestParam int commentId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.json2String(111, "用户未登录");
        }
        User self = hostHolder.getUser();
        if (followService.follow(self.getId(), JiuYeUtil.ENTITY_FLOOR, commentId)) {
            return JsonUtil.json2String(0);
        }
        return JsonUtil.json2String(1, "关注评论失败。");
    }

    @RequestMapping("/comment/no")
    @ResponseBody
    public String unfollowComment(@RequestParam int commentId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.json2String(111, "用户未登录");
        }
        User self = hostHolder.getUser();
        if (followService.unfollow(self.getId(), JiuYeUtil.ENTITY_FLOOR, commentId)) {
            return JsonUtil.json2String(0);
        }
        return JsonUtil.json2String(1, "取消关注评论失败。");
    }

    @RequestMapping("/get")
    @ResponseBody
    public String getFans(int entityType, int entityId) {
        List<Integer> fansId = followService.getFansId(entityType, entityId, 10);
        List<User> users = new ArrayList<>();
        for (int id : fansId) {
            users.add(userService.getUserById(id));
        }
        return users.toString();
    }

}
