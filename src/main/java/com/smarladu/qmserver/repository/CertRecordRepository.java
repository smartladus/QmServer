package com.smarladu.qmserver.repository;

import com.smarladu.qmserver.entity.CertRecord;
import com.smarladu.qmserver.entity.CertTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertRecordRepository extends MongoRepository<CertRecord, String> {
    CertRecord findByRecNo(String recNo);
    List<CertRecord> findByTaskNo(String taskNo);
}
