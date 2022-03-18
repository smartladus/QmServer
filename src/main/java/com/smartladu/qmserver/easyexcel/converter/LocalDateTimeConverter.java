package com.smartladu.qmserver.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Slf4j
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
    @Override
    public Class supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        log.info(cellData.getStringValue());
        return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    @Override
    public CellData convertToExcelData(LocalDateTime value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData<>(value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }
}
