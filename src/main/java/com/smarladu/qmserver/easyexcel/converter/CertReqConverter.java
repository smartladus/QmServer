package com.smarladu.qmserver.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.smarladu.qmserver.entity.certtask.CertReq;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/06/29
 */
public class CertReqConverter implements Converter<CertReq[]> {
    @Override
    public Class supportJavaTypeKey() {
        return CertReq[].class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public CertReq[] convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String[] arrCertTypeStr = cellData.getStringValue().split("\\|");
        CertReq[] certReqs = new CertReq[arrCertTypeStr.length];
        for (int i = 0; i < arrCertTypeStr.length; i++) {
            String[] paras = arrCertTypeStr[i].split(":");
            certReqs[i] = new CertReq(paras[0], paras[1]);
        }
        return certReqs;
    }

    @Override
    public CellData convertToExcelData(CertReq[] certReq, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }
}
