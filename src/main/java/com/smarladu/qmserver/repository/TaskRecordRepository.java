package com.smarladu.qmserver.repository;

import com.smarladu.qmserver.entity.certtask.TaskRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TaskRecordRepository {
    private static final String COLLECTION = "task_record";

    @Autowired
    private MongoTemplate mongoTemplate;

    public void replaceAll(List<TaskRecord> list) {
        mongoTemplate.dropCollection(COLLECTION);
        mongoTemplate.insert(list, COLLECTION);
    }

    public List<TaskRecord> findByTaskNo(String taskNo) {
        Query query = new Query(Criteria.where("task_no").is(taskNo));
        return mongoTemplate.find(query, TaskRecord.class, COLLECTION);
    }
}
