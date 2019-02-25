//package com.bupt.dc.dao.ibatis;
//
//import com.bupt.dc.dao.ibatis.service.UserMapper;
//import KeepAccountStartApplication;
//import User;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Date;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {KeepAccountStartApplication.class})
////@Transactional
//public class UserServiceTest {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    @Rollback
//    public void findByName() throws Exception {
//        userMapper.insert(1,"AAA", 20, new Date());
//        User u = userMapper.findByName("AAA");
//        Assert.assertEquals(20, u.getAge().intValue());
//    }
//
//}
