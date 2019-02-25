package com.bupt.dc.config.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class MongoAppender extends AppenderBase<ILoggingEvent> {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<BasicDBObject> logsCollection;

    private String connectionUrl;
    private String databaseName;
    private String collectionName;

    @Override
    protected void append(ILoggingEvent event) {
        log.info("=========append============");
        if(mongoDatabase == null) {
            MongoClientURI connectionString = new MongoClientURI(connectionUrl);
            mongoClient = new MongoClient(connectionString);
            mongoDatabase = mongoClient.getDatabase(databaseName);
            logsCollection = mongoDatabase.getCollection(collectionName, BasicDBObject.class);
        }
        logsCollection.insertOne(JSONObject.parseObject(event.getMessage(), BasicDBObject.class));
    }

    @Override
    public void stop() {
        log.info("==================mongoAppenderStop====================");
        if(mongoClient != null) {
            mongoClient.close();
        }
        //super.stop();
    }
}
