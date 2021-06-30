package com.smarladu.qmserver.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @program: QmServer
 * @description: 区域/国家缩写，及英文全称
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "region")
public class Region {
    @Id
    private String id;

    @ExcelProperty("abbr")
    private String abbr; // 区域/国家缩写，一般为2位

    @ExcelProperty("region_chs")
    private String region_chs; // 区域/国家中文全称
}