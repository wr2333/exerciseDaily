package com.example.jiuYe2.service;

import com.example.jiuYe2.dao.UserDAO;
import com.example.jiuYe2.model.User;
import com.example.jiuYe2.util.HostHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserDAO userDAO;

    @Resource
    HostHolder hostHolder;

    public User getUserById(Integer id) {
        return userDAO.selectById(id);
    }

    public String getUserPage() {
        return "This is a user page. welcome: " + hostHolder.getUser().getName();
    }
}
