package com.smarladu.qmserver.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smarladu.qmserver.easyexcel.converter.StringArrConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cert_category")
public class CertCategory implements Serializable {
    @Id
    private String id;

    @ExcelProperty("region")
    private String region; // 区域/国家缩写，一般为2位

    @ExcelProperty("cert_name")
    private String certName; // 认证名称

    @ExcelProperty(value = "type", converter = StringArrConverter.class)
    private String[] type; // 认证类型

    @ExcelProperty("compulsory")
    private boolean compulsory; // 是否强制认证
}
