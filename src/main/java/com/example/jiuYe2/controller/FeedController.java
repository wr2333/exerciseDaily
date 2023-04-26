package com.example.jiuYe2.controller;

import com.example.jiuYe2.model.Feed;
import com.example.jiuYe2.model.User;
import com.example.jiuYe2.service.FeedService;
import com.example.jiuYe2.service.FollowService;
import com.example.jiuYe2.util.HostHolder;
import com.example.jiuYe2.util.JedisAdapter;
import com.example.jiuYe2.util.RedisKeyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/feed")
public class FeedController {

    @Resource
    FeedService feedService;

    @Resource
    HostHolder hostHolder;

    @Resource
    FollowService followService;

    @Resource
    JedisAdapter jedisAdapter;

    @RequestMapping(value = "/getPull", method = RequestMethod.GET)
    @ResponseBody
    public String getFeedByUserIds() {
        User self = hostHolder.getUser();
        List<Integer> careIds = new ArrayList<>();
        if (self != null) {
            careIds = followService.getCareIds(self.getId(), Integer.MAX_VALUE);
        }
        List<Feed> feeds = feedService.getFeedByUserIds(careIds, Integer.MAX_VALUE, 10);
        return feeds.toString();
    }

    @RequestMapping(value = "/getPush", method = RequestMethod.GET)
    @ResponseBody
    public String getFeedByIds() {
        User self = hostHolder.getUser();
        int selfId = self != null ? self.getId() : 0;
        List<String> feedIds = jedisAdapter.lrange(RedisKeyUtil.getTimelineQueueKey(selfId), 0, 10);
        List<Feed> feeds = new ArrayList<>();
        for (String feedId : feedIds) {
            feeds.add(feedService.getFeedById(Integer.parseInt(feedId)));
        }
        return feeds.toString();
    }

}
