package com.smartladu.qmserver.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.smartladu.qmserver.easyexcel.converter.StringArrConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @program: QmServer
 * @description: 供应商类
 * @author: Eason Wu
 * @create: 2021/9/27
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "suppliers")
public class Supplier implements Serializable {
    @Id
    private String id;

    @ExcelProperty("供应商简称")
    private String abbr;

    @ExcelProperty("中文全名")
    private String compNameZh;

    @ExcelProperty("英文全名")
    private String compNameEn;

    @ExcelProperty("中文地址")
    private String addressZh;

    @ExcelProperty("英文地址")
    private String addressEn;

    @ExcelProperty("统一社会信用代码")
    private String compId;

    @ExcelProperty(value = "主要产品", converter = StringArrConverter.class)
    private String[] mainProducts;

    @ExcelProperty("早期不良率阈值")
    private double drThr150d;

    @ExcelProperty("1年不良率阈值")
    private double drThr1y;

    @ExcelProperty("2年不良率阈值")
    private double drThr2y;

    @ExcelProperty("3年不良率阈值")
    private double drThr3y;
}
