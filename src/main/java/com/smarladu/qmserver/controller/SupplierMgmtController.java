package com.smarladu.qmserver.controller;

import com.smarladu.qmserver.entity.Region;
import com.smarladu.qmserver.entity.Supplier;
import com.smarladu.qmserver.repository.cert.SupplierRepository;
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
 * @description: 供应商管理
 * @author: Eason Wu
 * @create: 2021/9/28
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/suppliers")
public class SupplierMgmtController {
    @Autowired
    private SupplierRepository supplierRepository;



    /**
     * ====================================================================================
     * 各类文件上传
     * ====================================================================================
     */
    // 上传供应商清单，默认为增量方式
    @PostMapping("/upload")
    public ApiResult importRegions(MultipartFile file, @RequestParam(value = "mode", required = false) String mode) {
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
            return ApiResult.fail("suppliers info upload failed: " + e.getMessage());
        }
    }


    /**
     * ====================================================================================
     * Supplier Api
     * ====================================================================================
     */



}
