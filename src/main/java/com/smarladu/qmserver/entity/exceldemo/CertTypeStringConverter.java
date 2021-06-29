package com.smarladu.qmserver.entity.exceldemo;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.smarladu.qmserver.entity.CertType;

import java.util.Arrays;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/06/29
 */
public class CertTypeStringConverter implements Converter<CertType[]> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public CertType[] convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String[] arrCertTypeStr = cellData.getStringValue().split("\\|");
        CertType[] certTypes = new CertType[arrCertTypeStr.length];
        for (int i = 0; i < arrCertTypeStr.length; i++) {
            String[] paras = arrCertTypeStr[i].split(":");
            certTypes[i] = new CertType(paras[0], paras[1]);
        }
        return certTypes;
    }

    @Override
    public CellData convertToExcelData(CertType[] certType, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }
}
