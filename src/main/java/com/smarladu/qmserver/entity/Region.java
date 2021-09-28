package com.smarladu.qmserver.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

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
public class Region implements Serializable {
    @Id
    private String id;

    @ExcelProperty("continent")
    private String continent; // 所属大洲

    @ExcelProperty("abbr")
    private String abbr; // 区域/国家缩写，一般为2位

    @ExcelProperty("region_chs")
    @JsonProperty("region_chs")
    private String regionChs; // 区域/国家中文全称
}
