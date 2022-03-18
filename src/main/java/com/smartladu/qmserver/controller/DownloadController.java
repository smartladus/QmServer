package com.smartladu.qmserver.controller;

import com.smartladu.qmserver.service.DownloadService;
import com.smartladu.qmserver.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: QmServer
 * @description: 管理文件下载业务
 * @author: Eason Wu
 * @create: 2022/2/10
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/download")
public class DownloadController {
    @Autowired
    DownloadService downloadService;

    /**
     * 提供各类清单模板下载
     * @param response 返回文件流
     */
    @RequestMapping("/template/{templateType}")
    @ResponseBody
    public void DownloadCertTaskListTemplate(
            HttpServletResponse response,
            @PathVariable(value = "templateType") String templateType
    ) {
        String fileUrl = downloadService.getDownloadFileUrl(templateType);
        if (fileUrl != null) {
            String result = FileUtil.downloadFile(response, fileUrl);
            log.info("{} 下载：{}", fileUrl, result);
        } else {
            log.info("错误的下载路径：{}", templateType);
        }
    }
}
