//package com.bupt.dc.dao.jpa;
//
//import KeepAccountStartApplication;
//import User;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {KeepAccountStartApplication.class})
//public class UserServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @Transactional
//    public void test() throws Exception {
//
//        // 创建10条记录
//        userRepository.save(new User(1L, "AAA", 10, LocalDate.now()));
//        userRepository.save(new User(2L,"BBB", 20, LocalDate.now()));
//        userRepository.save(new User(3L,"CCC", 30, LocalDate.now()));
//        userRepository.save(new User(4L,"DDD", 40, LocalDate.now()));
//        userRepository.save(new User(5L,"EEE", 50, LocalDate.now()));
//        userRepository.save(new User(6L,"FFF", 60, LocalDate.now()));
//        userRepository.save(new User(7L,"GGG", 70, LocalDate.now()));
//        userRepository.save(new User(8L,"HHH", 80, LocalDate.now()));
//        userRepository.save(new User(9L,"III", 90, LocalDate.now()));
//        userRepository.save(new User(10L,"JJJ", 100, LocalDate.now()));
//
//        // 测试findAll, 查询所有记录
//        Assert.assertEquals(10L, userRepository.findAll().size());
//
//        // 测试findByName, 查询姓名为FFF的User
//        Assert.assertEquals(60, userRepository.findByName("FFF").getAge().longValue());
//
//        // 测试findUser, 查询姓名为FFF的User
//        Assert.assertEquals(60, userRepository.findUser("FFF").getAge().longValue());
//
//        // 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的User
//        Assert.assertEquals("FFF", userRepository.findByNameAndAge("FFF", 60).getName());
//
//        // 测试删除姓名为AAA的User
//        userRepository.delete(userRepository.findByName("AAA"));
//
//        // 测试findAll, 查询所有记录, 验证上面的删除是否成功
//        Assert.assertEquals(9L, userRepository.findAll().size());
//
//    }
//
//}
