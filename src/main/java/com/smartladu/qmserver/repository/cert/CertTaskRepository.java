package com.smartladu.qmserver.repository.cert;

import com.smartladu.qmserver.entity.certtask.CertTask;
import com.smartladu.qmserver.entity.certtask.CertTaskRecord;
import com.smartladu.qmserver.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CertTaskRepository extends BaseRepository<CertTask> {
    @Override
    protected void setCollection() {
        collection = "cert_task";
    }

    @Autowired
    private CertRegionRepository certRegionRepository;

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
                return replaceAll(list).size();
            case "add":
                return addByTaskNo(list);
            default:
                return  -1;
        }
    }

    public int addByTaskNo(List<CertTask> list) {
        int count = 0;
        for(CertTask task : list) {
            Query query = new Query(Criteria.where("task_no").is(task.getTaskNo()));
            if (!mongoTemplate.exists(query, collection)) {
                mongoTemplate.insert(task, collection);
                count++;
            }
        }
        return count;
    }

    public CertTask insertTask(CertTask task) {
        if (task.getTaskNo() == null || task.getTaskNo().equals("new")) {
            task.setTaskNo(createTaskNo(task));
        }
        mongoTemplate.insert(task, collection);
        return task;
    }

    private String createTaskNo(CertTask task) {
        StringBuilder sb = new StringBuilder();
        String abbr = certRegionRepository.findOneByFieldVal("country", task.getRegion()[1]).getAbbr();
        sb.append(abbr);
        sb.append(task.getCertName());
        sb.append("X".repeat(4 - task.getCertName().length()));
        sb.append(task.getCertMethod().toUpperCase());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        sb.append(LocalDateTime.now().format(dateTimeFormatter));
        int i = 1;
        NumberFormat formatter = new DecimalFormat("000");
        while (isTaskNoExist(sb + formatter.format(i))) {
            i++;
        }
        return sb + formatter.format(i);
    }

    private boolean isTaskNoExist(String taskNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("task_no").is(taskNo));
        return mongoTemplate.find(query, CertTaskRecord.class, collection).size() > 0;
    }


    public List<String> getFuzzyTaskNo(String taskNoSeg) {
        ArrayList<String> res = new ArrayList<>();
        for (CertTask task : getFuzzyFieldList("task_no", taskNoSeg)) {
            res.add(task.getTaskNo());
        }
        return res;

    }

    public List<CertTask> getTaskListByPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Query query = new Query();
        return mongoTemplate.find(query.with(pageable), CertTask.class, collection);
    }
}
