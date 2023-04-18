package com.example.jiuYe2.controller;

import com.example.jiuYe2.service.UserService;
import com.example.jiuYe2.util.ViewObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping("/user/{id}")
    public List<ViewObject> getUserById(@PathVariable("id") Integer id) {
        List<ViewObject> vos = new ArrayList<>();
        ViewObject vo = new ViewObject();
        vo.put("user", userService.getUserById(id));
        vos.add(vo);
        return vos;
    }

}
