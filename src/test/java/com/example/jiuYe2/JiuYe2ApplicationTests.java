package com.example.jiuYe2;

import com.example.jiuYe2.dao.UserDAO;
import com.example.jiuYe2.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Random;

@SpringBootTest
class JiuYe2ApplicationTests {

    @Resource
    UserDAO userDAO;

    @Test
    void contextLoads() {
        Random random = new Random();

//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            user.setName("user" + i);
//            user.setPassword("pwd" + random.nextInt(1000));
//            userDAO.insert(user);
//        }

        System.out.println("修改前：" + userDAO.selectById(3).toString());
        User user = new User();
        user.setId(3);
        user.setName("user235");
        user.setPassword("pwd235");
        userDAO.updateById(user);
        System.out.println("修改后：" + userDAO.selectById(3).toString());

    }
}
