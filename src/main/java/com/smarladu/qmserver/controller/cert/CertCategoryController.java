package com.smarladu.qmserver.controller.cert;

import com.mongodb.client.result.DeleteResult;
import com.smarladu.qmserver.entity.CertCategory;
import com.smarladu.qmserver.repository.cert.CertCategoryRepository;
import com.smarladu.qmserver.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: QmServer
 * @description: 认证类型管理Api
 * @author: Eason Wu
 * @create: 2022/2/22
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/cert/categories")
public class CertCategoryController {
    @Autowired
    private CertCategoryRepository certCategoryRepository;

    /**
     * ====================================================================================
     * Category Api
     * ====================================================================================
     */

    // 获取所有认证类型
    @GetMapping("")
    public ApiResult getAllCertCategory(@RequestParam(value = "region", required = false) String region) {
        if (region == null) {
            return ApiResult.success(certCategoryRepository.getAll());
        } else {
            return ApiResult.success(certCategoryRepository.findByRegion(region));
        }
    }

    // 保存新增认证类型
    @PostMapping(value = "")
    public ApiResult insertCertCategory(@RequestBody CertCategory category) {
        return ApiResult.success(certCategoryRepository.insert(category));
    }

    // 更新指定认证类型
    @PutMapping("/{categoryId}")
    public ApiResult updateCategory(@PathVariable(value = "categoryId") String categoryId, @RequestBody CertCategory category) {
        if (!certCategoryRepository.existsByField("_id", categoryId)) {
            return ApiResult.fail(ApiResult.Code.RECORD_NOT_FOUND, "category " + category.getCertName() + " not found");
        }
        return ApiResult.success(
                "task " + category.getCertName() + " updated",
                certCategoryRepository.save(category)
        );
    }

    // 删除category
    @DeleteMapping("/{categoryId}")
    public ApiResult deleteCategory(@PathVariable(value = "categoryId") String categoryId) {
        DeleteResult res = certCategoryRepository.deleteByFieldVal("_id", categoryId);
        Long deletedCount = res.getDeletedCount();
        if (deletedCount.intValue() > 0) {
            return ApiResult.success("region " + categoryId + " deleted: " + deletedCount.intValue(), deletedCount);
        } else {
            return ApiResult.fail(ApiResult.Code.RECORD_NOT_FOUND,"fail to delete region, region not found with id: " + categoryId);
        }
    }

}
