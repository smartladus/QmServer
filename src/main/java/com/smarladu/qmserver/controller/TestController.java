package com.smarladu.qmserver.controller;

import com.alibaba.fastjson.JSON;
import com.smarladu.qmserver.entity.Supplier;
import com.smarladu.qmserver.entity.certtask.CertTask;
import com.smarladu.qmserver.repository.cert.CertTaskRepository;
import com.smarladu.qmserver.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/06/30
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private CertTaskRepository certTaskRepository;

    @GetMapping("/msg")
    public String getTestMsg() {
        return  "this is test msg";
    }

    @GetMapping("/task")
    public List<CertTask> getTaskByPage(@RequestParam(value = "page_no") int pageNo, @RequestParam(value = "page_size") int pageSize) {
        return certTaskRepository.getTaskListByPage(pageNo, pageSize);
    }

    @GetMapping("/supplier")
    public ApiResult getSupplier() {
        Supplier supplier1 = new Supplier("xxx", "起立", "起立科技有限公司", "拼接屏|监视器");
        Supplier supplier2 = new Supplier("xxx", "捷翔", "起立科技有限公司", "拼接屏|监视器");
        ArrayList<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier1);
        suppliers.add(supplier2);

        return ApiResult.success(JSON.toJSON(suppliers));
    }
}
