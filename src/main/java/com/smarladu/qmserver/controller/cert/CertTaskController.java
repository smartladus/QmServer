package com.smarladu.qmserver.controller.cert;

import com.mongodb.client.result.DeleteResult;
import com.smarladu.qmserver.entity.certtask.TaskRecord;
import com.smarladu.qmserver.entity.certtask.CertTask;
import com.smarladu.qmserver.repository.cert.CertTaskRecordRepository;
import com.smarladu.qmserver.repository.cert.CertTaskRepository;
import com.smarladu.qmserver.result.ApiResult;
import com.smarladu.qmserver.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.smarladu.qmserver.result.ApiResult.Code;

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
public class CertTaskController {
    @Autowired
    private CertTaskRecordRepository taskRecordRepository;

    @Autowired
    private CertTaskRepository certTaskRepository;


    /**
     * ====================================================================================
     * 各类文件上传
     * ====================================================================================
     */

    // 上传认证任务记录，默认为增量方式
    @PostMapping("/upload/records")
    public ApiResult importCertRecord(
            MultipartFile file,
            @RequestParam(value = "mode", required = false, defaultValue = "add") String mode) {
        try {
            ArrayList<TaskRecord> list = ExcelUtil.getExcelData(file, TaskRecord.class);
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
            return ApiResult.fail(Code.UPLOAD_ERROR, "records upload failed: " + e.getMessage());
        }
    }

    // 上传认证任务，默认为增量方式
    @PostMapping("/upload/tasks")
    public ApiResult importCertTask(MultipartFile file, @RequestParam(value = "mode", required = false, defaultValue = "add") String mode) {
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
            return ApiResult.fail(Code.UPLOAD_ERROR, "tasks upload failed: " + e.getMessage());
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
            return ApiResult.fail(Code.RECORD_NOT_FOUND, "task " + taskNo + " not found");
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
            return ApiResult.fail(Code.RECORD_NOT_FOUND,"Task " + taskNo + " not found");
        }
    }

    // 删除指定记录
    @DeleteMapping("/records/{recNo}")
    public ApiResult deleteTaskRecord(@PathVariable String recNo) {
        if (!taskRecordRepository.existsByField("record_no", recNo)) {
            return ApiResult.fail(Code.RECORD_NOT_FOUND, "Record " + recNo + " not found");
        }
        return ApiResult.success(
                "Record " + recNo + " deleted",
                taskRecordRepository.deleteByFieldVal("record_no", recNo)
        );
    }

    // 更新指定的任务记录
    @PutMapping("/records/{recNo}")
    public ApiResult updateTaskRecord(@PathVariable String recNo, @RequestBody TaskRecord record) {
        String errMsg = null;
        if (!taskRecordRepository.existsByField("record_no", recNo)) {
            return ApiResult.fail(Code.RECORD_NOT_FOUND, "Record " + recNo + " not found");
        }
        return ApiResult.success(
                "Record " + recNo + " updated",
                taskRecordRepository.save(record)
        );
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

    // 获取所有任务的指定字段清单
    @GetMapping("/tasks/fields/{fieldName}")
    public ApiResult getFieldOfAllCertTask(@PathVariable String fieldName) {
        List<Object> taskNos = certTaskRepository.getFieldList(fieldName);
        return ApiResult.success(taskNos);
    }

    // 获取指定任务
    @GetMapping("/tasks/{taskNo}")
    public ApiResult getCertTask(@PathVariable String taskNo) {
        CertTask task = certTaskRepository.findOneByFieldVal("task_no", taskNo);
        if (task != null) {
            return ApiResult.success(task);
        }
        return ApiResult.fail(Code.RECORD_NOT_FOUND, "Task " + taskNo + " not found");
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
            return ApiResult.fail(Code.RECORD_NOT_FOUND, "fail to delete task " + taskNo);
        }
    }

    // 更新指定任务
    @PutMapping("/tasks/{taskNo}")
    public ApiResult updateTask(@PathVariable String taskNo, @RequestBody CertTask certTask) {
        if (!certTaskRepository.existsByField("task_no", taskNo)) {
            return ApiResult.fail(Code.RECORD_NOT_FOUND, "task " + taskNo + " not found");
        }
        return ApiResult.success(
                "task " + taskNo + " updated",
                certTaskRepository.save(certTask)
        );
    }
}
