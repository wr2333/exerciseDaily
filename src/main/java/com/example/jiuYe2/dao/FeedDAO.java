package com.example.jiuYe2.dao;

import com.example.jiuYe2.model.Feed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FeedDAO {

    int addFeed(Feed feed);

    List<Feed> getFeedByUserIds(@Param("userIds") List<Integer> userIds, @Param("maxId") int maxId, @Param("count") int count);

    Feed getFeedById(int id);

}