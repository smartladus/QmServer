package com.smarladu.qmserver.repository;

import com.smarladu.qmserver.entity.CertCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Repository
public class CertCategoryRepository {
    private static final String COLLECTION = "cert_category";

    @Autowired
    private MongoTemplate mongoTemplate;

    public void replaceAll(List<CertCategory> list) {
        mongoTemplate.dropCollection(COLLECTION);
        mongoTemplate.insert(list, COLLECTION);
    }

    public List<CertCategory> findByRegion(String abbr) {
        Query query = new Query(Criteria.where("region").is(abbr));
        return mongoTemplate.find(query, CertCategory.class, COLLECTION);
    }
}
