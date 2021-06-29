package com.smarladu.qmserver.controller;

import com.smarladu.qmserver.entity.CertRecord;
import com.smarladu.qmserver.entity.CertTask;
import com.smarladu.qmserver.repository.CertRecordRepository;
import com.smarladu.qmserver.repository.CertTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/mongo")
public class MongoDBController {
    @Autowired
    private CertRecordRepository certRecordRepository;

    @Autowired
    private CertTaskRepository certTaskRepository;

    @GetMapping("/test/get/all")
    public List<CertRecord> getAll() {
        return certRecordRepository.findAll();
    }

    @GetMapping("/test/get/recno/{recNo}")
    public CertRecord getByRecNo(@PathVariable String recNo) {
        return  certRecordRepository.findByRecNo(recNo);
    }

    @GetMapping("/test/get/taskno/{taskNo}")
    public List<CertRecord> getByTaskNo(@PathVariable String taskNo) {
        return  certRecordRepository.findByTaskNo(taskNo);
    }

    @PostMapping(value = "/test/inserttask", produces = "application/json;charset=UTF-8")
    public void insertTask(@RequestBody CertTask certTask) {
        log.info(certTask.toString());
        certTaskRepository.insert(certTask);
    }
}
