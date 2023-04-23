package com.example.jiuYe2.dao;

import com.example.jiuYe2.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDAO {

    int addComment(Comment comment);

    // 当接口参数大于一个时，需要注解标明变量名。
    List<Comment> getCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    int getCommentCountByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    int updateStatus(@Param("id") int id, @Param("status") int status);

    Comment getCommentById(int id);

}