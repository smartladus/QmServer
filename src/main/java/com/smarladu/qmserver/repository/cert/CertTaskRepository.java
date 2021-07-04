package com.smarladu.qmserver.repository.cert;

import com.smarladu.qmserver.entity.certtask.CertTask;
import com.smarladu.qmserver.repository.base.BaseRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertTaskRepository extends BaseRepository<CertTask> {
    @Override
    protected void setCollection() {
        collection = "cert_kanban";
    }

    /**
     * 根据任务编号删除任务
     * @param taskNo 任务编号
     * @return 成功删除的数量
     */
    public long deleteTaskByTaskNo(String taskNo) {
        Query query = new Query(Criteria.where("task_no").is(taskNo));
        return mongoTemplate.remove(query, collection).getDeletedCount();
    }

    public int uploadTasks(String mode, List<CertTask> list) {
        switch (mode) {
            case "replace":
                return replaceAll(list);
            case "add":
                return addByTaskNo(list);
            default:
                return  -1;
        }
    }

    public int addByTaskNo(List<CertTask> list) {
        int count = 0;
        for(CertTask task : list) {
            Query query = new Query(Criteria.where("task_no").is(task.getTask_no()));
            if (!mongoTemplate.exists(query, collection)) {
                mongoTemplate.insert(task, collection);
                count++;
            }
        }
        return count;
    }
}
