package com.smarladu.qmserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


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
    @GetMapping("/msg")
    public String getTestMsg() {
        return  "this is test msg";
    }
}
