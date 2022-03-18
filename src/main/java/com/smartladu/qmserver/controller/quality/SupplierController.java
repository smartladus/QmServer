package com.smartladu.qmserver.controller.quality;

import com.mongodb.client.result.DeleteResult;
import com.smartladu.qmserver.repository.quality.SupplierRepository;
import com.smartladu.qmserver.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.smartladu.qmserver.result.ApiResult.Code;

/**
 * @program: QmServer
 * @description: 供应商管理
 * @author: Eason Wu
 * @create: 2021/9/28
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/quality/suppliers")
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;

    // 获取供应商清单
    @GetMapping("")
    public ApiResult getAllSuppliers() {
        return ApiResult.success(supplierRepository.getAll());
    }

    // 删除region
    @DeleteMapping("/{supplierId}")
    public ApiResult deleteSupplier(@PathVariable(value = "supplierId") String supplierId) {
        DeleteResult res = supplierRepository.deleteByFieldVal("_id", supplierId);
        Long deletedCount = res.getDeletedCount();
        if (deletedCount.intValue() > 0) {
            return ApiResult.success("supplier " + supplierId + " deleted: " + deletedCount.intValue(), deletedCount);
        } else {
            return ApiResult.fail(Code.RECORD_NOT_FOUND, "fail to delete supplier, supplier not found with id: " + supplierId);
        }
    }

}
