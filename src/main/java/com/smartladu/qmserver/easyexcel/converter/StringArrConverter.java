package com.smartladu.qmserver.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/6/30
 */

public class StringArrConverter implements Converter<String[]> {
    @Override
    public Class supportJavaTypeKey() {
        return String[].class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String[] convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return cellData.getStringValue().split("\\|");
    }

    @Override
    public CellData convertToExcelData(String[] strings, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }
}
