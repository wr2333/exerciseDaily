package com.example.jiuYe2.service;


import com.example.jiuYe2.dao.CommentDAO;
import com.example.jiuYe2.model.Comment;
import com.example.jiuYe2.util.HealthUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentService {

    @Resource
    CommentDAO commentDAO;

    @Resource
    HealthUtil healthUtil;

    public int addComment(Comment comment) {
        // 防止html和js语句
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        // 和谐敏感词
        comment.setContent(healthUtil.filter(comment.getContent()));
        return commentDAO.addComment(comment) > 0 ? 1 : 0;
    }

    public List<Comment> getCommentByEntity(int entityId, int entityType) {
        return commentDAO.getCommentByEntity(entityId, entityType);
    }

    public int getCommentCountByEntity(int entityId, int entityType) {
        return commentDAO.getCommentCountByEntity(entityId, entityType);
    }

    public boolean deleteComment(int id) {
        return commentDAO.updateStatus(id, 1) > 0;
    }

    public Comment getCommentById(int id) {
        return commentDAO.getCommentById(id);
    }

}
