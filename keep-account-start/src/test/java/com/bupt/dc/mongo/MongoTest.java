package com.bupt.dc.mongo;

import com.alibaba.fastjson.JSONObject;
import com.bupt.dc.config.config.mongo.PrimaryMongoConfig;
import com.bupt.dc.dao.mongo.primary.HttpRequestRepository;
import com.bupt.dc.object.mongo.HttpRequestObj;
import com.bupt.dc.start.KeepAccountStartApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {KeepAccountStartApplication.class})
public class MongoTest {

    @Autowired
    private HttpRequestRepository httpRequestRepository;

    @Resource(name = PrimaryMongoConfig.MONGO_TEMPLATE)
    private MongoTemplate primaryMongoTemplate;

    @Before
    public void setUp() {
    }

    @Test
    public void test() throws Exception {
        System.out.println("************************************************************");
        System.out.println("测试开始");
        System.out.println("************************************************************");

        //List<HttpRequestObj> objs = httpRequestRepository.findAll(PageRequest.of(0,1)).getContent();
        List<HttpRequestObj> objs = httpRequestRepository.findBy(PageRequest.of(0,1)).getContent();

        Aggregation agg = Aggregation.newAggregation(Aggregation.match(
                                                            Criteria.where("requestURL").is("http://localhost:1111/home/")
                                                        ),
                                                        Aggregation.group("_id")
                                                                    .count().as("count")
                                                                    .last("requestURL").as("lastUrl"));
        List<HttpRequestObj> aggObjs = primaryMongoTemplate.aggregate(agg, "logs_request", HttpRequestObj.class).getMappedResults();

        System.out.println(JSONObject.toJSONString(objs));
        System.out.println(JSONObject.toJSONString(aggObjs));
        System.out.println("************************************************************");
        System.out.println("测试完成");
        System.out.println("************************************************************");

    }

}
