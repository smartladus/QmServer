package com.smarladu.qmserver.entity.exceldemo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.smarladu.qmserver.entity.CertType;
import lombok.Data;
import lombok.ToString;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/06/29
 */

@Data
@ToString
public class Student {
    private String id;

    @ExcelProperty(converter = CertTypeStringConverter.class)
    private CertType[] certTypes;
}
