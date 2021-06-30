package com.smarladu.qmserver.repository;

import com.smarladu.qmserver.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Repository
public class RegionRepository {
    private static final String COLLECTION = "region";

    @Autowired
    private MongoTemplate mongoTemplate;

    public void replaceAll(List<Region> regionList) {
        mongoTemplate.dropCollection(COLLECTION);
        mongoTemplate.insert(regionList, COLLECTION);
    }
}
