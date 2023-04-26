package com.example.jiuYe2;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.jiuYe2.service.CommentService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

// 新版本测试类写法，必须加public
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JiuYe2Application.class)
public class JiuYe2ApplicationTests {

    @Resource
    CommentService commentService;

    // @Before和@After会在每个测试方法前后执行一次，不用是static。
    // @BeforeClass和@AfterClass只会在整个测试开始前后执行一次，必须为static。
    @BeforeClass
    public static void setUp() {
        System.out.println("测试前准备");
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("测试后清理");
    }

    @Test
    public void testComment() {
        System.out.println("测试接口中");
        Assert.assertEquals(9, commentService.getCommentCountByEntity(1, 1));
    }

    @Test(expected = NullPointerException.class)
    public void testException() {
        System.out.println("测试异常中");
        throw new NullPointerException("你是一个空指针啊啊");
    }

}
