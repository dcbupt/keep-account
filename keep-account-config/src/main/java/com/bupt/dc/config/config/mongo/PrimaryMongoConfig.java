package com.bupt.dc.config.config.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.bupt.dc.dao.mongo.primary",
    mongoTemplateRef = PrimaryMongoConfig.MONGO_TEMPLATE)
public class PrimaryMongoConfig {

    public static final String MONGO_TEMPLATE = "primaryMongoTemplate";

}
