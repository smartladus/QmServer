package com.smarladu.qmserver.repository.cert;

import com.smarladu.qmserver.entity.certtask.TaskRecord;
import com.smarladu.qmserver.repository.base.BaseRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TaskRecordRepository extends BaseRepository<TaskRecord> {
    @Override
    protected void setCollection() {
        collection = "task_record";
    }

    public List<TaskRecord> findByTaskNo(String taskNo) {
        Query query = new Query(Criteria.where("task_no").is(taskNo));
        return mongoTemplate.find(query, TaskRecord.class, collection);
    }
}
