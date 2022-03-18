package com.smartladu.qmserver.controller.cert;

import com.mongodb.client.result.DeleteResult;
import com.smartladu.qmserver.entity.certtask.CertTaskRecord;
import com.smartladu.qmserver.entity.certtask.CertTask;
import com.smartladu.qmserver.repository.cert.CertTaskRecordRepository;
import com.smartladu.qmserver.repository.cert.CertTaskRepository;
import com.smartladu.qmserver.repository.cert.CertTaskStatRepository;
import com.smartladu.qmserver.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.smartladu.qmserver.result.ApiResult.Code;

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
    private CertTaskRepository taskRepository;

    @Autowired
    private CertTaskStatRepository taskStatRepository;

    /**
     * ====================================================================================
     * Task State Api
     * ====================================================================================
     */

    @GetMapping("/tasks/stats")
    public ApiResult getAllTaskStats() {
        return ApiResult.success("", taskStatRepository.getAll());
    }

    /**
     * ====================================================================================
     * Task Record Api
     * ====================================================================================
     */

    // 获取指定任务的所有历史记录，未指定任务编号则返回所有记录
    @GetMapping("/tasks/records")
    public ApiResult getAllRecords() {
        return ApiResult.success("", taskRecordRepository.getAll());
    }

    // 获取指定任务的所有历史记录，未指定任务编号则返回所有记录
    @GetMapping("/tasks/{taskNo}/records")
    public ApiResult getRecordsOfTask(@PathVariable String taskNo) {
        return taskRepository.existsByField("task_no", taskNo)
                ? ApiResult.success("", taskRecordRepository.findByFieldVal("task_no", taskNo, "record_time", false))
                : ApiResult.fail(Code.RECORD_NOT_FOUND, "task " + taskNo + " not found");
    }

    // 保存新增历史记录
    @PostMapping("/tasks/records")
    public ApiResult saveRecordOfTask(@RequestBody CertTaskRecord record) {
        String taskNo = record.getTaskNo();
        if (taskRepository.existsByField("task_no", taskNo)) {
            return ApiResult.success(
                    "new record for task " + taskNo + " saved",
                    taskRecordRepository.insertRecord(record));
        } else {
            return ApiResult.fail(Code.RECORD_NOT_FOUND,"Task " + taskNo + " not found");
        }
    }

    // 删除指定记录
    @DeleteMapping("/tasks/records/{recNo}")
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
    @PutMapping("/tasks/records/{recNo}")
    public ApiResult updateTaskRecord(@PathVariable String recNo, @RequestBody CertTaskRecord record) {
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
        return ApiResult.success(taskRepository.getAll("start_date", false));
    }

    // 获取所有任务的指定字段清单
    @GetMapping("/tasks/fields/{fieldName}")
    public ApiResult getFieldOfAllCertTask(@PathVariable String fieldName) {
        List<Object> taskNos = taskRepository.getFieldList(fieldName);
        return ApiResult.success(taskNos);
    }

    // 获取指定任务
    @GetMapping("/tasks/{taskNo}")
    public ApiResult getCertTask(@PathVariable String taskNo) {
        CertTask task = taskRepository.findOneByFieldVal("task_no", taskNo);
        if (task != null) {
            return ApiResult.success(task);
        }
        return ApiResult.fail(Code.RECORD_NOT_FOUND, "Task " + taskNo + " not found");
    }

    // 保存新增任务
    @PostMapping(value = "/tasks")
    public ApiResult insertTask(@RequestBody CertTask certTask) {
        certTask.setStartDate(new Date());
        log.info(certTask.getTaskNo());
        return ApiResult.success(taskRepository.insertTask(certTask));
    }

    // 删除指定任务
    @DeleteMapping("/tasks/{taskNo}")
    public ApiResult deleteTaskByTaskNo(@PathVariable String taskNo) {
        DeleteResult res = taskRepository.deleteByFieldVal("task_no", taskNo);
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
        if (!taskRepository.existsByField("task_no", taskNo)) {
            return ApiResult.fail(Code.RECORD_NOT_FOUND, "task " + taskNo + " not found");
        }
        return ApiResult.success(
                "task " + taskNo + " updated",
                taskRepository.save(certTask)
        );
    }
}
