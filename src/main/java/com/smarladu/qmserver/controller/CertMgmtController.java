package com.smarladu.qmserver.controller;

import com.mongodb.client.result.DeleteResult;
import com.smarladu.qmserver.entity.certtask.TaskRecord;
import com.smarladu.qmserver.entity.certtask.CertTask;
import com.smarladu.qmserver.entity.Region;
import com.smarladu.qmserver.entity.CertCategory;
import com.smarladu.qmserver.repository.cert.CertCategoryRepository;
import com.smarladu.qmserver.repository.cert.TaskRecordRepository;
import com.smarladu.qmserver.repository.cert.CertTaskRepository;
import com.smarladu.qmserver.repository.cert.RegionRepository;
import com.smarladu.qmserver.utils.ExcelUtil;
import com.smarladu.qmserver.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: QmServer
 * @description: 认证管理模块Controller
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/cert")
public class CertMgmtController {
    @Autowired
    private TaskRecordRepository taskRecordRepository;

    @Autowired
    private CertTaskRepository certTaskRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CertCategoryRepository certCategoryRepository;


    @PostMapping("/task/record/upload")
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

    @GetMapping("/task/record/get/all")
    public List<TaskRecord> getAllTaskRecord() {
        return  taskRecordRepository.getAll();
    }

    @GetMapping("/task/record/get/{taskNo}")
    public List<TaskRecord> getTaskRecordByTaskNo(@PathVariable String taskNo) {
        return  taskRecordRepository.findByTaskNo(taskNo);
    }

    @PostMapping(value = "/task/record/insert", produces = "application/json;charset=UTF-8")
    public TaskRecord insertTaskRecord(@RequestBody TaskRecord record) {
        return taskRecordRepository.insertRecord(record);
    }

    @PostMapping(value = "/task/record/update")
    public TaskRecord updateTaskRecord(@RequestBody TaskRecord record) {
        return taskRecordRepository.save(record);
    }

    @DeleteMapping(value = "/task/record/delete/{recNo}")
    public DeleteResult deleteTaskRecord(@PathVariable String recNo) {
        return taskRecordRepository.deleteByFieldVal("record_no", recNo);
    }

    @PostMapping("/task/upload")
    @ResponseBody
    public int importCertTask(MultipartFile file, @RequestParam(value = "mode") String mode) {
        try {
            ArrayList<CertTask>list = ExcelUtil.getExcelData(file, CertTask.class);
            return certTaskRepository.uploadTasks(mode, list);
        } catch (IOException e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @GetMapping("/task/get/all")
    public List<CertTask> getAllCertTask() {
        return  certTaskRepository.getAll();
    }

    @GetMapping("/task/no/get/{taskNoSeg}")
    public List<String> getRelatedCertTaskNo(@PathVariable String taskNoSeg) {
        return  certTaskRepository.getFuzzyTaskNo(taskNoSeg);
    }

    @GetMapping("/task/get/{taskNo}")
    public CertTask getCertTask(@PathVariable String taskNo) {
        return certTaskRepository.findOneByFieldVal("task_no", taskNo);
    }

    @PostMapping(value = "/task/insert", produces = "application/json;charset=UTF-8")
    public CertTask insertTask(@RequestBody CertTask certTask) {
        return certTaskRepository.insertTask(certTask);
    }

    @PostMapping(value = "/task/update")
    public CertTask updateTask(@RequestBody CertTask certTask) {
        return certTaskRepository.save(certTask);
    }

    @DeleteMapping("/task/delete/{taskNo}")
    public long deleteTaskByTaskNo(@PathVariable String taskNo) {
        return certTaskRepository.deleteTaskByTaskNo(taskNo);
    }

    @RequestMapping("/task/download/template")
    @ResponseBody
    public void DownloadCertTaskListTemplate(HttpServletResponse response) {
        String fileName = "认证任务清单模板.xlsx";
        String result = FileUtil.downloadFile(response, fileName);
        log.info("{} 下载：{}", fileName, result);
    }

    @GetMapping("/region/get/all")
    public List<Region> getAllRegion() {
        return  regionRepository.getAll();
    }

    @PostMapping(value = "/region")
    public Region saveRegion(@RequestBody Region region) {
        return regionRepository.save(region);
    }

    @PostMapping("/region/upload")
    public int importRegions(MultipartFile file, @RequestParam(value = "mode") String mode ) {
        try {
            ArrayList<Region>list = ExcelUtil.getExcelData(file, Region.class);
            if (mode.equals("replace")) {
                return regionRepository.replaceAll(list);
            } else {
                return regionRepository.saveAll(list);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @DeleteMapping(value = "/region")
    public DeleteResult deleteRegion(@RequestBody Region region) {
        return regionRepository.deleteByFieldVal("_id", region.getId());
    }

    @GetMapping("/category/get/all")
    public List<CertCategory> getAllCertCategory() {
        return  certCategoryRepository.getAll();
    }

    @GetMapping("/category/get")
    public List<CertCategory> getCertCategoryByRegion(@RequestParam("region") String region) {
        return  certCategoryRepository.findByRegion(region);
    }

    @PostMapping("/category/upload")
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
