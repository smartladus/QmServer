package com.smartladu.qmserver.controller;

import com.smartladu.qmserver.entity.CertCategory;
import com.smartladu.qmserver.entity.Region;
import com.smartladu.qmserver.entity.Supplier;
import com.smartladu.qmserver.entity.certtask.CertTask;
import com.smartladu.qmserver.entity.certtask.CertTaskRecord;
import com.smartladu.qmserver.entity.certtask.CertTaskStat;
import com.smartladu.qmserver.repository.cert.*;
import com.smartladu.qmserver.repository.cert.CertRegionRepository;
import com.smartladu.qmserver.repository.quality.SupplierRepository;
import com.smartladu.qmserver.result.ApiResult;
import com.smartladu.qmserver.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @program: QmServer
 * @description: 管理文件上传业务
 * @author: Eason Wu
 * @create: 2022/2/10
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CertCategoryRepository certCategoryRepository;

    @Autowired
    private CertRegionRepository regionRepository;

    @Autowired
    private CertTaskRepository certTaskRepository;

    @Autowired
    private CertTaskRecordRepository taskRecordRepository;

    @Autowired
    private CertTaskStatRepository certTaskStatRepository;

    /**
     * 通过上传文件导入供应商信息
     * @param file 供应商清单文件
     * @param mode 导入模式，add或者replace，默认为增量
     * @return ApiResult
     */
    @PostMapping("/suppliers")
    public ApiResult importSuppliers(
            MultipartFile file,
            @RequestParam(value = "mode", required = false, defaultValue = "add") String mode
    ) {
        try {
            ArrayList<Supplier> list = ExcelUtil.getExcelData(file, Supplier.class);
            Collection<Supplier> suppliers;
            String msg = null;
            if (mode.equals("replace")) {
                suppliers = supplierRepository.replaceAll(list);
                msg = "suppliers info replaced, count: " + suppliers.size();
            } else {
                suppliers = supplierRepository.saveAll(list);
                msg = "suppliers info added, count: " + suppliers.size();
            }
            return ApiResult.success(msg, suppliers);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ApiResult.fail(ApiResult.Code.UPLOAD_ERROR, "suppliers info upload failed: " + e.getMessage());
        }
    }

    // 上传认证类型
    @PostMapping("/categories")
    public ApiResult importCertCategories(MultipartFile file, @RequestParam(value = "mode", required = false, defaultValue = "add") String mode) {
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
            return ApiResult.fail(ApiResult.Code.UPLOAD_ERROR, "categories upload failed: " + e.getMessage());
        }
    }

    // 上传认证区域
    @PostMapping("/regions")
    public ApiResult importCertRegions(MultipartFile file, @RequestParam(value = "mode", required = false, defaultValue = "add") String mode) {
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
            return ApiResult.fail(ApiResult.Code.UPLOAD_ERROR, "regions upload failed: " + e.getMessage());
        }
    }

    // 上传认证任务
    @PostMapping("/tasks")
    public ApiResult importCertTasks(MultipartFile file, @RequestParam(value = "mode", required = false, defaultValue = "add") String mode) {
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
            return ApiResult.fail(ApiResult.Code.UPLOAD_ERROR, "tasks upload failed: " + e.getMessage());
        }
    }

    // 上传认证任务记录，默认为增量方式
    @PostMapping("/tasks/records")
    public ApiResult importCertTaskRecords(
            MultipartFile file,
            @RequestParam(value = "mode", required = false, defaultValue = "add") String mode) {
        try {
            ArrayList<CertTaskRecord> list = ExcelUtil.getExcelData(file, CertTaskRecord.class);
            Collection<CertTaskRecord> records;
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
            return ApiResult.fail(ApiResult.Code.UPLOAD_ERROR, "records upload failed: " + e.getMessage());
        }
    }

    // 上传认证任务状态清单，默认为增量方式
    @PostMapping("/tasks/stats")
    public ApiResult importCertTaskStats(
            MultipartFile file,
            @RequestParam(value = "mode", required = false, defaultValue = "add") String mode) {
        try {
            ArrayList<CertTaskStat> list = ExcelUtil.getExcelData(file, CertTaskStat.class);
            Collection<CertTaskStat> stats;
            String msg;
            if (mode.equals("replace")) {
                stats = certTaskStatRepository.replaceAll(list);
                msg = "records replaced, count: " + stats.size();
            } else {
                stats = certTaskStatRepository.saveAll(list);
                msg = "records added, count: " + stats.size();
            }
            return ApiResult.success(msg, stats);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ApiResult.fail(ApiResult.Code.UPLOAD_ERROR, "status list upload failed: " + e.getMessage());
        }
    }
}
