package com.example.jiuYe2.dao;

import com.example.jiuYe2.model.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDAO {

    int addComment(Comment comment);

}