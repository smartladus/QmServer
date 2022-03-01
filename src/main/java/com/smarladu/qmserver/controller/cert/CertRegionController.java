package com.smarladu.qmserver.controller.cert;

import com.mongodb.client.result.DeleteResult;
import com.smarladu.qmserver.entity.Region;
import com.smarladu.qmserver.repository.cert.CertRegionRepository;
import com.smarladu.qmserver.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: QmServer
 * @description: 认证区域管理Api
 * @author: Eason Wu
 * @create: 2022/2/22
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/cert/regions")
public class CertRegionController {

    @Autowired
    private CertRegionRepository regionRepository;

    // 获取region清单
    @GetMapping("")
    public ApiResult getAllRegions() {
        return ApiResult.success(regionRepository.getAll());
    }

    // 保存新增的region
    @PostMapping("")
    public ApiResult saveRegion(@RequestBody Region region) {
        String errMsg = null;
        int errCode = -1;
        if (regionRepository.existsByField("abbr", region.getAbbr())) {
            errMsg = "prop of region duplicated: abbr -> " + region.getAbbr();
            errCode = ApiResult.Code.INVALID_PARAM;
        }
        if (regionRepository.existsByField("region_chs", region.getCountry())) {
            errMsg = "prop of region duplicated: region_chs -> " + region.getCountry();
            errCode = ApiResult.Code.INVALID_PARAM;
        }
        if (errMsg == null) {
            Region res = regionRepository.insert(region);
            return ApiResult.success("region created: " + res.toString(), res);
        } else {
            return ApiResult.fail(errCode, errMsg);
        }
    }

    // 更新region属性值
    @PutMapping("/{regionId}")
    public ApiResult updateRegion(@PathVariable(value = "regionId") String regionId, @RequestBody Region region) {
        if (regionRepository.existsByField("_id", regionId)) {
            return ApiResult.success("region " + regionId + " updated: " + region.toString(), regionRepository.save(region));
        } else {
            return ApiResult.fail(ApiResult.Code.RECORD_NOT_FOUND, "fail to update region, region not found with id: " + regionId);
        }
    }

    // 删除region
    @DeleteMapping("/{regionId}")
    public ApiResult deleteRegion(@PathVariable(value = "regionId") String regionId) {
        DeleteResult res = regionRepository.deleteByFieldVal("_id", regionId);
        Long deletedCount = res.getDeletedCount();
        if (deletedCount.intValue() > 0) {
            return ApiResult.success("region " + regionId + " deleted: " + deletedCount.intValue(), deletedCount);
        } else {
            return ApiResult.fail(ApiResult.Code.RECORD_NOT_FOUND,"fail to delete region, region not found with id: " + regionId);
        }
    }
}
