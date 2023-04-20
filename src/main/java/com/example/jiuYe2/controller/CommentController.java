package com.example.jiuYe2.controller;


import com.example.jiuYe2.model.Comment;
import com.example.jiuYe2.service.CommentService;
import com.example.jiuYe2.util.HostHolder;
import com.example.jiuYe2.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource
    CommentService commentService;

    @Resource
    HostHolder hostHolder;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addComment(@RequestParam String content) {
        Comment comment = new Comment();
        comment.setUserId(hostHolder.getUser().getId());
        comment.setEntityId(1);
        comment.setEntityType(1);
        comment.setContent(content);
        if (commentService.addComment(comment) > 0) {
            return JsonUtil.json2String(0);
        }
        return JsonUtil.json2String(1, "评论添加失败");
    }


}
