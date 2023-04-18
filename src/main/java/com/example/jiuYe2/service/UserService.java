package com.example.jiuYe2.service;

import com.example.jiuYe2.dao.UserDAO;
import com.example.jiuYe2.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserDAO userDAO;

    public User getUserById(Integer id) {
        return userDAO.selectById(id);
    }

}
