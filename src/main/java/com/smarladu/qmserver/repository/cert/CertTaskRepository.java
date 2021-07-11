package com.smarladu.qmserver.repository.cert;

import com.smarladu.qmserver.entity.certtask.CertTask;
import com.smarladu.qmserver.repository.base.BaseRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    public List<String> getFuzzyTaskNo(String taskNoSeg) {
        ArrayList<String> res = new ArrayList<>();
        for (CertTask task : getFuzzyFieldList("task_no", taskNoSeg)) {
            res.add(task.getTask_no());
        }
        return res;

    }

    public List<CertTask> getTaskListByPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Query query = new Query();
        return mongoTemplate.find(query.with(pageable), CertTask.class, collection);
    }
}
