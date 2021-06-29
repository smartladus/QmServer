package com.smarladu.qmserver.utils;

import com.alibaba.excel.EasyExcel;
import com.smarladu.qmserver.entity.exceldemo.Student;
import com.smarladu.qmserver.entity.exceldemo.StudentListener;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/06/29
 */
public class ExcelUtil {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Eason\\IdeaProjects\\QmServer\\demo.xlsx";
        EasyExcel.read(
                filePath,
                Student.class,
                new StudentListener()
        ).sheet().doRead();
    }
}
