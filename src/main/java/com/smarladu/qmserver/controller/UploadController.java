package com.smarladu.qmserver.controller;

import com.smarladu.qmserver.entity.CertCategory;
import com.smarladu.qmserver.entity.Region;
import com.smarladu.qmserver.entity.Supplier;
import com.smarladu.qmserver.repository.cert.CertCategoryRepository;
import com.smarladu.qmserver.repository.cert.CertRegionRepository;
import com.smarladu.qmserver.repository.quality.SupplierRepository;
import com.smarladu.qmserver.result.ApiResult;
import com.smarladu.qmserver.utils.ExcelUtil;
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
}
