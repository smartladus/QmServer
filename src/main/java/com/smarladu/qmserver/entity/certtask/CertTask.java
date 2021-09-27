package com.smarladu.qmserver.entity.certtask;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String taskNo; // 认证流水号

    @ExcelProperty("oa_no")
    private String oaNo; // 外部认证申请单号

    @ExcelProperty("cost")
    private int cost; // 认证费用

    @ExcelProperty("cost_bearer")
    private String costBearer; // 费用承担方

    @ExcelProperty("sup_name")
    private String supName; // 供应商

    @ExcelProperty("region")
    private String region; // 认证区域

    @ExcelProperty("cert_owner")
    private String certOwner; // 证书归属

    @ExcelProperty("cert_name")
    private String certName; // 认证类型

    @ExcelProperty("cert_method")
    private String certMethod; // 获证方式

    @ExcelProperty("cert_method_desc")
    private String certMethodDesc; // 获证方式说明，例如RoHS SDoC是通过零部件清单进行的

    @ExcelProperty("jv_model")
    private String jvModel; // JV型号范围

    @ExcelProperty("sup_model")
    private String supModel; // 供应商型号范围

    @ExcelProperty("task_stat")
    private String taskStat; // 任务状态

    @ExcelProperty("start_date")
    private Date startDate; // 开始日期

    @ExcelProperty("end_date")
    private Date endDate; // 结束日期

    @ExcelProperty("cert_no")
    private String certNo; // 证书编号

    @ExcelProperty(value = "todo")
    private String todo; // 待办事项

    @ExcelProperty(value = "comments")
    private String comments; // 备注
}
