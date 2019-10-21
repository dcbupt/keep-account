//package com.bupt.dc.control.controller;
//
//import com.bupt.dc.dao.jpa.RoleRepository;
//import com.bupt.dc.dao.jpa.UserRepository;
//import com.bupt.dc.object.auth.User;
//import mockit.Expectations;
//import mockit.Injectable;
//import mockit.Tested;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//
//public class UserAuthControllerTest {
//
//    @Tested
//    UserAuthController userAuthController;
//
//    @Injectable
//    UserRepository userRepository;
//
//    @Injectable
//    RoleRepository roleRepository;
//
//    @Injectable
//    PasswordEncoder passwordEncoder;
//
//    @Test
//    public void getUserListTest() {
//        List<User> users = new ArrayList<>();
//        User user = new User(1L, "dc", "123", new Date());
//        new Expectations() {
//            {
//                userRepository.findAll();
//                users.add(user);
//                result = users;
//            }
//        };
//        //Assert.assertTrue(userAuthController.getUserList().get(0).getUsername().equals("dc"));
//        //Assert.assertEquals(1, userAuthController.getUserList().size());
//        Assert.assertEquals(users, userAuthController.getUserList());
//    }
//
//    @Test
//    public void loginTest() {
//        User request = new User(1L, "dc", "123", new Date());
//        User db1 = new User(1L, "dc", "123", new Date());
//        new Expectations() {
//            {
//                userRepository.findByUsername("dc");
//                result = db1;
//                passwordEncoder.matches(request.getPassword(), db1.getPassword());
//                result = true;
//            }
//        };
//        Assert.assertTrue(userAuthController.login(request));
//
//        User db2 = new User(1L, "dc", "456", new Date());
//        new Expectations() {
//            {
//                userRepository.findByUsername("dc");
//                result = db2;
//                passwordEncoder.matches(request.getPassword(), db2.getPassword());
//                result = false;
//            }
//        };
//        Assert.assertFalse(userAuthController.login(request));
//    }
//
//}
