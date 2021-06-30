package com.smarladu.qmserver.entity.certtask;

import com.alibaba.excel.annotation.ExcelProperty;
import com.smarladu.qmserver.easyexcel.converter.CertReqConverter;
import com.smarladu.qmserver.easyexcel.converter.StringArrConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cert_kanban")
public class CertTask implements Serializable {
    @Id
    private String id;

    @ExcelProperty("task_no")
    private String task_no; // 认证流水号

    @ExcelProperty("oa_no")
    private String oa_no; // 外部认证申请单号

    @ExcelProperty("cost")
    private int cost; // 认证费用

    @ExcelProperty("cost_bearer")
    private String cost_bearer; // 费用承担方

    @ExcelProperty("sup_name")
    private String sup_name; // 供应商

    @ExcelProperty("region")
    private String region; // 认证区域

    @ExcelProperty("cert_owner")
    private String cert_owner; // 证书归属

    @ExcelProperty(value = "cert_req", converter = CertReqConverter.class)
    private CertReq[] cert_req; // 认证类型和方式

    @ExcelProperty("jv_model")
    private String jv_model; // JV型号范围

    @ExcelProperty("sup_model")
    private String sup_model; // 供应商型号范围

    @ExcelProperty("task_stat")
    private String task_stat; // 任务状态

    @ExcelProperty("start_date")
    private Date start_date; // 开始日期

    @ExcelProperty("end_date")
    private Date end_date; // 结束日期

    @ExcelProperty(value = "cert_no", converter = StringArrConverter.class)
    private String[] cert_no; // 证书编号

    @ExcelProperty("todo")
    private String todo; // 待办事项

    @ExcelProperty("comments")
    private String comments; // 备注
}
