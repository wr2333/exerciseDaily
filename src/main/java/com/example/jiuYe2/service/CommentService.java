package com.example.jiuYe2.service;


import com.example.jiuYe2.dao.CommentDAO;
import com.example.jiuYe2.model.Comment;
import com.example.jiuYe2.util.HealthUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;

@Service
public class CommentService {

    @Resource
    CommentDAO commentDAO;

    @Resource
    HealthUtil healthUtil;

    public int addComment(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent())); //防止html和js语句
        comment.setContent(healthUtil.filter(comment.getContent()));    //和谐敏感词
        return commentDAO.addComment(comment);
    }
}
