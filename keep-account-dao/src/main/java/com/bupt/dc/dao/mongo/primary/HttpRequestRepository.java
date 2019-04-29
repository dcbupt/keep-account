package com.bupt.dc.dao.mongo.primary;

import com.bupt.dc.object.mongo.HttpRequestObj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface HttpRequestRepository extends MongoRepository<HttpRequestObj, String> {

    @Query(fields = "{'requestURL':1}")
    Page<HttpRequestObj> findBy(Pageable pageable);
}
