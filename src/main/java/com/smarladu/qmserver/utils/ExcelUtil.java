package com.smarladu.qmserver.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.smarladu.qmserver.entity.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/06/29
 */
@Slf4j
public class ExcelUtil {
    public static <T> ArrayList<T> getExcelData(String path, Class<T> rowModel) {
        ArrayList<T> list = new ArrayList<>();
        EasyExcel.read(
                path,
                rowModel,
                new AnalysisEventListener<T>() {
                    @Override
                    public void invoke(T t, AnalysisContext analysisContext) {
                        list.add(t);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        log.info(path + " 数据读取完毕，共" + list.size() + "个数据");
                    }
                }
        ).sheet().doRead();
        return list;
    }

    public static <T> ArrayList<T> getExcelData(MultipartFile file, Class<T> rowModel) throws IOException {
        ArrayList<T> list = new ArrayList<>();
        EasyExcel.read(
                file.getInputStream(),
                rowModel,
                new AnalysisEventListener<T>() {
                    @Override
                    public void invoke(T t, AnalysisContext analysisContext) {
                        list.add(t);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        log.info(file.getName() + " 数据读取完毕，共" + list.size() + "个数据");
                    }
                }
        ).sheet().doRead();
        return list;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Codes\\IdeaProjects\\QmServer\\db_data\\region.xlsx";
        System.out.println(getExcelData(filePath, Region.class));
    }
}
