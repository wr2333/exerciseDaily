package com.example.jiuYe2;

import com.example.jiuYe2.dao.UserDAO;
import com.example.jiuYe2.model.User;
import com.example.jiuYe2.util.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest
class JiuYe2ApplicationTests {

    @Resource
    UserDAO userDAO;

    @Test
    void contextLoads() {

        for (int i = 2; i <= 11; i++) {
            User user = userDAO.selectById(i);
            user.setSalt(UUID.randomUUID().toString().substring(0, 5));
            user.setPassword(Md5Util.Md5(user.getPassword() + user.getSalt()));
            userDAO.updateById(user);
        }

    }
}
