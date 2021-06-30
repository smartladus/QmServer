package com.smarladu.qmserver.controller;

import com.smarladu.qmserver.entity.certtask.TaskRecord;
import com.smarladu.qmserver.entity.certtask.CertTask;
import com.smarladu.qmserver.entity.Region;
import com.smarladu.qmserver.entity.CertCategory;
import com.smarladu.qmserver.repository.CertCategoryRepository;
import com.smarladu.qmserver.repository.TaskRecordRepository;
import com.smarladu.qmserver.repository.CertTaskRepository;
import com.smarladu.qmserver.repository.RegionRepository;
import com.smarladu.qmserver.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/mongo")
public class MongoDBController {
    @Autowired
    private TaskRecordRepository taskRecordRepository;

    @Autowired
    private CertTaskRepository certTaskRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CertCategoryRepository certCategoryRepository;


    @PostMapping("/cert/task/record/upload")
    @ResponseBody
    public String importCertRecord(MultipartFile file) {
        try {
            ArrayList<TaskRecord>list = ExcelUtil.getExcelData(file, TaskRecord.class);
            taskRecordRepository.replaceAll(list);
            return "SUCCESS";
        } catch (IOException e) {
            log.error(e.getMessage());
            return "FAIL";
        }
    }

    @GetMapping("/cert/task/record/get")
    public List<TaskRecord> getCertRecordByRegion(@RequestParam("task_no") String taskNo) {
        return  taskRecordRepository.findByTaskNo(taskNo);
    }

    @PostMapping("/cert/task/upload")
    @ResponseBody
    public String importCertTask(MultipartFile file) {
        try {
            ArrayList<CertTask>list = ExcelUtil.getExcelData(file, CertTask.class);
            certTaskRepository.replaceAll(list);
            return "SUCCESS";
        } catch (IOException e) {
            log.error(e.getMessage());
            return "FAIL";
        }
    }

    @PostMapping(value = "/cert/task/insert", produces = "application/json;charset=UTF-8")
    public void insertTask(@RequestBody CertTask certTask) {
        log.info(certTask.toString());
        certTaskRepository.insert(certTask);
    }

    @PostMapping("/cert/region/upload")
    @ResponseBody
    public String importRegion(MultipartFile file) {
        try {
            ArrayList<Region>list = ExcelUtil.getExcelData(file, Region.class);
            regionRepository.replaceAll(list);
            return "SUCCESS";
        } catch (IOException e) {
            log.error(e.getMessage());
            return "FAIL";
        }
    }

    @GetMapping("/cert/category/get")
    public List<CertCategory> getCertCategoryByRegion(@RequestParam("region") String region) {
        return  certCategoryRepository.findByRegion(region);
    }

    @PostMapping("/cert/category/upload")
    @ResponseBody
    public String importCertCategory(MultipartFile file) {
        try {
            ArrayList<CertCategory>list = ExcelUtil.getExcelData(file, CertCategory.class);
            certCategoryRepository.replaceAll(list);
            return "SUCCESS";
        } catch (IOException e) {
            log.error(e.getMessage());
            return "FAIL";
        }
    }
}
