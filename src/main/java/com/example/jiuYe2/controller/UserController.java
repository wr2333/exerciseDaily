package com.example.jiuYe2.controller;

import com.example.jiuYe2.service.UserService;
import com.example.jiuYe2.util.ViewObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping("/{id}")
    public List<ViewObject> getUserById(@PathVariable("id") Integer id) {
        List<ViewObject> vos = new ArrayList<>();
        ViewObject vo = new ViewObject();
        vo.put("user", userService.getUserById(id));
        vos.add(vo);
        return vos;
    }

    @RequestMapping("/page")
    @ResponseBody
    public String getUserPage() {
        return userService.getUserPage();
    }

}
