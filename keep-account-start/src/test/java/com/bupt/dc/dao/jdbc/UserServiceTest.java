//package com.bupt.dc.dao.jdbc;
//
//import com.bupt.dc.dao.jdbc.service.UserService;
//import KeepAccountStartApplication;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Date;
//
////@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {KeepAccountStartApplication.class})
//public class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Before
//    public void setUp() {
//        // 准备，清空user表
//        userService.deleteAllUsers();
//    }
//
//    @Test
//    public void test() throws Exception {
//        // 插入5个用户
//        userService.createUserDetails("a", 1, new Date());
//        userService.createUserDetails("b", 2, new Date());
//        userService.createUserDetails("c", 3, new Date());
//        userService.createUserDetails("d", 4, new Date());
//        userService.createUserDetails("e", 5, new Date());
//
//        // 查数据库，应该有5个用户
//        Assert.assertEquals(5, userService.getAllUsers().intValue());
//
//        // 删除两个用户
//        userService.deleteByName("a");
//        userService.deleteByName("e");
//
//        // 查数据库，应该有5个用户
//        Assert.assertEquals(3, userService.getAllUsers().intValue());
//
//    }
//
//}
