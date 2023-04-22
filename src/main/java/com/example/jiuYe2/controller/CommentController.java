package com.example.jiuYe2.controller;


import com.example.jiuYe2.model.Comment;
import com.example.jiuYe2.service.CommentService;
import com.example.jiuYe2.service.UserService;
import com.example.jiuYe2.util.HostHolder;
import com.example.jiuYe2.util.JiuYeUtil;
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
@RequestMapping("/comment")
public class CommentController {

    @Resource
    UserService userService;

    @Resource
    CommentService commentService;

    @Resource
    HostHolder hostHolder;

    // 添加主楼评论
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addComment(@RequestParam String content) {
        Comment comment = new Comment();
        if (hostHolder.getUser() != null) {
            comment.setUserId(hostHolder.getUser().getId());
        } else {
            // 匿名用户
            comment.setUserId(JiuYeUtil.ANONYMOUS_USER);
        }
        comment.setEntityId(JiuYeUtil.MAIN_FLOOR);
        comment.setEntityType(JiuYeUtil.ENTITY_FLOOR);
        comment.setContent(content);
        if (commentService.addComment(comment) > 0) {
            return JsonUtil.json2String(0);
        }
        return JsonUtil.json2String(1, "评论添加失败。");
    }

    @RequestMapping("/get")
    @ResponseBody
    public String getComment() {
        List<Comment> comments = commentService.getCommentByEntity(JiuYeUtil.MAIN_FLOOR, JiuYeUtil.ENTITY_FLOOR);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : comments) {
            ViewObject vo = new ViewObject();
            vo.put("comment", comment);
            vo.put("user", userService.getUserById(comment.getUserId()));
            vos.add(vo);
        }
        StringBuilder sb = new StringBuilder();
        for (ViewObject temp : vos) {
            sb.append(temp.vo2String()).append("\n");
        }
        return sb.toString();
    }


}
