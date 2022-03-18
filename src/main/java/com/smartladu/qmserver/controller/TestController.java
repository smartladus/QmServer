package com.smartladu.qmserver.controller;

import com.smartladu.qmserver.entity.certtask.CertTask;
import com.smartladu.qmserver.repository.cert.CertTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
