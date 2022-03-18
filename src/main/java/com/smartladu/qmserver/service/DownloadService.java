package com.smartladu.qmserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2022/2/22
 */

@Slf4j
@Service
public class DownloadService {
    public String getDownloadFileUrl(String templateType) {
        // 这里可以设置下载路径的prefix，没有就留空
        StringBuilder url = new StringBuilder("");
        switch (templateType) {
            case "categories":
                url.append("认证类型清单模板.xlsx");
                break;
            case "tasks":
                url.append("认证任务清单模板.xlsx");
                break;
            case "regions":
                url.append("认证区域清单模板.xlsx");
                break;
            default:
                return null;
        }
        return url.toString();
    }
}
