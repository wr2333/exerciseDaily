package com.example.jiuYe2.util;

import com.example.jiuYe2.model.User;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {

    // 为每个用户线程分配用户变量，用于存放该用户对象
    private static final ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }

}
