//package com.bupt.dc.mongo;
//
//import KeepAccountStartApplication;
//import User;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.regex.Pattern;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {KeepAccountStartApplication.class})
//public class MongoTest {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Before
//    public void setUp() {
//    }
//
//    @Test
//    public void test() throws Exception {
//
//        // 创建三个User，并验证User总数
//        mongoTemplate.save(new User(1L, "didi", 30));
//        mongoTemplate.save(new User(2L, "mama", 40));
//        mongoTemplate.save(new User(3L, "kaka", 50));
//        Assert.assertEquals(3, mongoTemplate.findAll(User.class).size());
//
//
//        Pattern pattern = Pattern.compile("^.*didi.*$", Pattern.CASE_INSENSITIVE);
//        Query query = Query.query(Criteria.where("name").regex(pattern));
//        Assert.assertEquals(1, mongoTemplate.find(query, User.class).size());
//
//        //// 删除一个User，再验证User总数
//        //Optional<User> u = userServiceMongo.findById(1L);
//        //userServiceMongo.delete(u.get());
//        //Assert.assertEquals(2, userServiceMongo.findAll().size());
//        //
//        //// 删除一个User，再验证User总数
//        //User u1 = userServiceMongo.findByName("mama");
//        //userServiceMongo.delete(u1);
//        //Assert.assertEquals(1, userServiceMongo.findAll().size());
//
//    }
//
//}
