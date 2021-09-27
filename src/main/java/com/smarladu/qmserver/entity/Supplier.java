package com.smarladu.qmserver.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/9/27
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Supplier implements Serializable {
    @Id
    private String id;

    @ExcelProperty("abbr")
    private String abbr; // 供应商简称

    @ExcelProperty("fullname")
    private String fullname; // 供应商全称

    @ExcelProperty("main_products")
    private String mainProducts; // 主要供应产品
}
