package com.example.jiuYe2.service;

import com.example.jiuYe2.dao.FeedDAO;
import com.example.jiuYe2.model.Feed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedService {

    @Resource
    FeedDAO feedDAO;

    // feed拉模式
    public boolean addFeed(Feed feed) {
        return feedDAO.addFeed(feed) > 0;
    }

    // feed拉模式
    public List<Feed> getFeedByUserIds(List<Integer> userIds, int maxId, int count) {
        return feedDAO.getFeedByUserIds(userIds, maxId, count);
    }

    // feed推模式
    public Feed getFeedById(int id) {
        return feedDAO.getFeedById(id);
    }

}
