package com.example.jiuYe2.controller;

import com.example.jiuYe2.service.UserService;
import com.example.jiuYe2.util.ViewObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping("/{id}")
    @ResponseBody
    public String getUserById(@PathVariable("id") Integer id) {
        ViewObject vo = new ViewObject();
        vo.put("user", userService.getUserById(id));
        return vo.get("user").toString();
    }

    @RequestMapping("/page")
    @ResponseBody
    public String getUserPage() {
        return userService.getUserPage();
    }

}
