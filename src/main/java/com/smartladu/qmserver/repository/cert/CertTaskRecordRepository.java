package com.smartladu.qmserver.repository.cert;

import com.smartladu.qmserver.entity.certtask.CertTaskRecord;
import com.smartladu.qmserver.repository.base.BaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Repository
public class CertTaskRecordRepository extends BaseRepository<CertTaskRecord> {
    @Override
    protected void setCollection() {
        collection = "cert_task_record";
    }

    public List<CertTaskRecord> findByTaskNo(String taskNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("task_no").is(taskNo))
                .with(Sort.by(Sort.Order.desc("record_time")));
        return mongoTemplate.find(query, CertTaskRecord.class, collection);
    }

    public CertTaskRecord insertRecord(CertTaskRecord record) {
        CertTaskRecord recordCopy = record;
        if (recordCopy.getRecordNo() == null) {
            recordCopy.setRecordNo(createRecNo());
        }
        mongoTemplate.insert(recordCopy, collection);
        return recordCopy;
    }

    private String createRecNo() {
        StringBuilder sb = new StringBuilder("REC");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        sb.append(LocalDateTime.now().format(dateTimeFormatter));
        int i = 1;
        NumberFormat formatter = new DecimalFormat("00000");
        while (isRecNoExist(sb + formatter.format(i))) {
            i++;
        }
        return sb + formatter.format(i);
    }

    private boolean isRecNoExist(String recNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("record_no").is(recNo));
        return mongoTemplate.find(query, CertTaskRecord.class, collection).size() > 0;
    }
}
