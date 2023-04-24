package com.example.jiuYe2.service;

import com.example.jiuYe2.dao.LoginTicketDAO;
import com.example.jiuYe2.dao.UserDAO;
import com.example.jiuYe2.model.LoginTicket;
import com.example.jiuYe2.model.User;
import com.example.jiuYe2.util.Md5Util;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RegLogService {

    @Resource
    UserDAO userDAO;

    @Resource
    LoginTicketDAO loginTicketDAO;

    public Map<String, String> register(String name, String password) {
        // 返回提示信息，注册成功则返回map内无msg。
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isEmpty(name)){
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)){
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDAO.getUserByName(name);
        if (user != null) {
            map.put("msg", "用户已注册");
            return map;
        }
        user = new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(Md5Util.Md5(password + user.getSalt()));
        userDAO.insert(user);

        String ticket = addTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public String addTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        Date now = new Date();
        // getTime()返回为毫秒值，一天格式为1000*3600*24。
        now.setTime(now.getTime() + 1000*3600*24*7);
        ticket.setExpired(now);
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public Map<String, String> login(String name, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isEmpty(name)){
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)){
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.getUserByName(name);
        if (user == null) {
            map.put("msg", "用户不存在");
            return map;
        }
        if (!user.getPassword().equals(Md5Util.Md5(password + user.getSalt()))) {
            map.put("msg", "密码错误");
            return map;
        }
        String ticket = addTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public String getLoginPage() {
        return "This is a login page.";
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }
}
