package com.smarladu.qmserver.repository;

import com.smarladu.qmserver.entity.certtask.CertTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertTaskRepository {
    private static final String COLLECTION = "cert_kanban";

    @Autowired
    private MongoTemplate mongoTemplate;

    public void replaceAll(List<CertTask> list) {
        mongoTemplate.dropCollection(COLLECTION);
        mongoTemplate.insert(list, COLLECTION);
    }

    public void insert(CertTask certTask) {
        mongoTemplate.insert(certTask, COLLECTION);
    }
}
