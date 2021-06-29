package com.smarladu.qmserver.entity.exceldemo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/06/29
 */
public class StudentListener extends AnalysisEventListener<Student> {
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        System.out.println(student);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
