package com.smarladu.qmserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.mongodb.MongoWriteException;
import com.mongodb.client.result.DeleteResult;
import com.smarladu.qmserver.entity.certtask.TaskRecord;
import com.smarladu.qmserver.entity.certtask.CertTask;
import com.smarladu.qmserver.entity.Region;
import com.smarladu.qmserver.entity.CertCategory;
import com.smarladu.qmserver.repository.cert.CertCategoryRepository;
import com.smarladu.qmserver.repository.cert.TaskRecordRepository;
import com.smarladu.qmserver.repository.cert.CertTaskRepository;
import com.smarladu.qmserver.repository.cert.RegionRepository;
import com.smarladu.qmserver.result.ApiResult;
import com.smarladu.qmserver.utils.ExcelUtil;
import com.smarladu.qmserver.utils.FileUtil;
import com.smarladu.qmserver.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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


    /**
     * ====================================================================================
     * 各类文件上传
     * ====================================================================================
     */

    // 上传认证区域，默认为增量方式
    @PostMapping("/upload/regions")
    public ApiResult importRegions(MultipartFile file, @RequestParam(value = "mode", required = false) String mode) {
        try {
            ArrayList<Region>list = ExcelUtil.getExcelData(file, Region.class);
            Collection<Region> regions;
            String msg = null;
            if (mode.equals("replace")) {
                regions = regionRepository.replaceAll(list);
                msg = "regions replaced, count: " + regions.size();
            } else {
                regions = regionRepository.saveAll(list);
                msg = "regions added, count: " + regions.size();
            }
            return ApiResult.success(msg, regions);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ApiResult.fail("regions upload failed: " + e.getMessage());
        }
    }

    // 上传认证任务记录，默认为增量方式
    @PostMapping("/upload/records")
    public ApiResult importCertRecord(MultipartFile file, @RequestParam(value = "mode", required = false) String mode) {
        try {
            ArrayList<TaskRecord>list = ExcelUtil.getExcelData(file, TaskRecord.class);
            Collection<TaskRecord> records;
            String msg = null;
            if (mode.equals("replace")) {
                records = taskRecordRepository.replaceAll(list);
                msg = "records replaced, count: " + records.size();
            } else {
                records = taskRecordRepository.saveAll(list);
                msg = "records added, count: " + records.size();
            }
            return ApiResult.success(msg, records);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ApiResult.fail("records upload failed: " + e.getMessage());
        }
    }

    // 上传认证任务，默认为增量方式
    @PostMapping("/upload/tasks")
    public ApiResult importCertTask(MultipartFile file, @RequestParam(value = "mode") String mode) {
        try {
            ArrayList<CertTask>list = ExcelUtil.getExcelData(file, CertTask.class);
            Collection<CertTask> tasks;
            String msg = null;
            if (mode.equals("replace")) {
                tasks = certTaskRepository.replaceAll(list);
                msg = "tasks replaced, count: " + tasks.size();
            } else {
                tasks = certTaskRepository.saveAll(list);
                msg = "tasks added, count: " + tasks.size();
            }
            return ApiResult.success(msg, tasks);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ApiResult.fail("tasks upload failed: " + e.getMessage());
        }
    }

    // 上传认证类型
    @PostMapping("/upload/categories")
    public ApiResult importCertCategory(MultipartFile file, @RequestParam(value = "mode", required = false) String mode) {
        try {
            ArrayList<CertCategory>list = ExcelUtil.getExcelData(file, CertCategory.class);
            Collection<CertCategory> categories;

            String msg = null;
            if (mode.equals("replace")) {
                categories = certCategoryRepository.replaceAll(list);
                msg = "categories replaced, count: " + categories.size();
            } else {
                categories = certCategoryRepository.saveAll(list);
                msg = "categories added, count: " + categories.size();
            }
            return ApiResult.success(msg, categories);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ApiResult.fail("categories upload failed: " + e.getMessage());
        }
    }
    /**
     * ====================================================================================
     * Regions Api
     * ====================================================================================
     */

    // 获取region清单
    @GetMapping("/regions")
    public ApiResult getAllRegions() {
        return ApiResult.success(regionRepository.getAll());
    }

    // 保存新增的region
    @PostMapping("/regions")
    public ApiResult saveRegion(@RequestBody Region region) {
        String errMsg = null;
        if (regionRepository.existsByField("abbr", region.getAbbr())) {
            errMsg = "prop of region duplicated: abbr -> " + region.getAbbr();
        }
        if (regionRepository.existsByField("region_chs", region.getRegionChs())) {
            errMsg = "prop of region duplicated: region_chs -> " + region.getRegionChs();
        }
        if (errMsg == null) {
            Region res = regionRepository.insert(region);
            return ApiResult.success("region created: " + res.toString(), res);
        } else {
            return ApiResult.fail(errMsg);
        }
    }

    // 更新region属性值
    @PutMapping("/regions/{regionId}")
    public ApiResult updateRegion(@PathVariable(value = "regionId") String regionId, @RequestBody Region region) {
        if (regionRepository.existsByField("_id", regionId)) {
            return ApiResult.success("region " + regionId + " updated: " + region.toString(), regionRepository.save(region));
        } else {
            return ApiResult.fail("region not found with id: " + regionId);
        }
    }

    // 删除region
    @DeleteMapping("/regions/{regionId}")
    public ApiResult deleteRegion(@PathVariable(value = "regionId") String regionId) {
        DeleteResult res = regionRepository.deleteByFieldVal("_id", regionId);
        Long deletedCount = res.getDeletedCount();
        if (deletedCount.intValue() > 0) {
            return ApiResult.success("region " + regionId + " deleted: " + deletedCount.intValue(), deletedCount);
        } else {
            return ApiResult.fail("fail to delete region, region not found with id: " + regionId);
        }
    }

    /**
     * ====================================================================================
     * Task Record Api
     * ====================================================================================
     */

    // 获取指定任务的所有历史记录，未指定任务编号则返回所有记录
    @GetMapping("/records")
    public ApiResult getRecordsOfTask(@RequestParam(value = "task_no", required = false) String taskNo) {
        if (taskNo == null) {
            return ApiResult.success("", taskRecordRepository.getAll());
        }
        if (certTaskRepository.existsByField("task_no", taskNo)) {
            return ApiResult.success("", taskRecordRepository.findByFieldVal("task_no", taskNo, "record_time", false));
        } else {
            return ApiResult.fail("task " + taskNo + " not found");
        }
    }

    // 保存新增历史记录
    @PostMapping("/records")
    public ApiResult saveRecordOfTask(@RequestBody TaskRecord record) {
        String taskNo = record.getTaskNo();
        if (certTaskRepository.existsByField("task_no", taskNo)) {
            return ApiResult.success(
                    "new record for task " + taskNo + " saved",
                    taskRecordRepository.insertRecord(record));
        } else {
            return ApiResult.fail("Task " + taskNo + " not found");
        }
    }

    // 删除指定记录
    @DeleteMapping("/records/{recNo}")
    public ApiResult deleteTaskRecord(@PathVariable String recNo) {
        String errMsg = null;
        if (!taskRecordRepository.existsByField("record_no", recNo)) {
            errMsg = "Record " + recNo + " not found";
        }
        if (errMsg == null) {
            return ApiResult.success(
                    "Record " + recNo + " deleted",
                    taskRecordRepository.deleteByFieldVal("record_no", recNo)
            );
        } else {
            return ApiResult.fail(errMsg);
        }
    }

    // 更新指定的任务记录
    @PutMapping("/records/{recNo}")
    public ApiResult updateTaskRecord(@PathVariable String recNo, @RequestBody TaskRecord record) {
        String errMsg = null;
        if (!taskRecordRepository.existsByField("record_no", recNo)) {
            errMsg = "Record " + recNo + " not found";
        }
        if (errMsg == null) {
            return ApiResult.success(
                    "Record " + recNo + " updated",
                    taskRecordRepository.save(record)
            );
        } else {
            return ApiResult.fail(errMsg);
        }
    }

    /**
     * ====================================================================================
     * Task Api
     * ====================================================================================
     */
    // 获取所有任务
    @GetMapping("/tasks")
    public ApiResult getAllCertTask() {
        return ApiResult.success(certTaskRepository.getAll("start_date", false));
    }

    // 获取所有任务号
    @GetMapping("/tasks/tasknos")
    public ApiResult getAllCertTaskNos() {
        List<String> taskNos = certTaskRepository.getAll().stream()
                .map(CertTask::getTaskNo)
                .collect(Collectors.toList());
        return ApiResult.success(taskNos);
    }

    // 获取指定任务
    @GetMapping("/tasks/{taskNo}")
    public ApiResult getCertTask(@PathVariable String taskNo) {
        CertTask task = certTaskRepository.findOneByFieldVal("task_no", taskNo);
        if (task != null) {
            return ApiResult.success(task);
        }
        return ApiResult.fail("Task " + taskNo + " not found");
    }

    // 保存新增任务
    @PostMapping(value = "/tasks")
    public ApiResult insertTask(@RequestBody CertTask certTask) {
        certTask.setStartDate(new Date());
        return ApiResult.success(certTaskRepository.insertTask(certTask));
    }

    // 删除指定任务
    @DeleteMapping("/tasks/{taskNo}")
    public ApiResult deleteTaskByTaskNo(@PathVariable String taskNo) {
        DeleteResult res = certTaskRepository.deleteByFieldVal("task_no", taskNo);
        Long deletedCount = res.getDeletedCount();
        if (deletedCount.intValue() > 0) {
            return ApiResult.success("task " + taskNo + " deleted", deletedCount);
        } else {
            return ApiResult.fail("fail to delete task " + taskNo);
        }
    }

    // 更新指定任务
    @PutMapping("/tasks/{taskNo}")
    public ApiResult updateTask(@PathVariable String taskNo, @RequestBody CertTask certTask) {
        String errMsg = null;
        if (!certTaskRepository.existsByField("task_no", taskNo)) {
            errMsg = "task " + taskNo + " not found";
        }
        if (errMsg == null) {
            return ApiResult.success(
                    "task " + taskNo + " updated",
                    certTaskRepository.save(certTask)
            );
        } else {
            return ApiResult.fail(errMsg);
        }
    }

    /**
     * ====================================================================================
     * Category Api
     * ====================================================================================
     */

    // 获取所有认证类型
    @GetMapping("/categories")
    public ApiResult getAllCertCategory(@RequestParam(value = "region", required = false) String region) {
        if (region == null) {
            return ApiResult.success(certCategoryRepository.getAll());
        } else {
            return ApiResult.success(certCategoryRepository.findByRegion(region));
        }
    }

    //=================================

    @GetMapping("/task/no/get/{taskNoSeg}")
    public List<String> getRelatedCertTaskNo(@PathVariable String taskNoSeg) {
        return  certTaskRepository.getFuzzyTaskNo(taskNoSeg);
    }






    @RequestMapping("/task/download/template")
    @ResponseBody
    public void DownloadCertTaskListTemplate(HttpServletResponse response) {
        String fileName = "认证任务清单模板.xlsx";
        String result = FileUtil.downloadFile(response, fileName);
        log.info("{} 下载：{}", fileName, result);
    }


}
