package com.smarladu.qmserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cert_kanban")
public class CertTask implements Serializable {
    @Id
    private String id;

    @Field("task_no")
    private String taskNo; // 认证流水号

    @Field("oa_no")
    private String oaNo; // 外部认证申请单号

    private int cost; // 认证费用

    @Field("cost_bearer")
    private String costBearer; // 费用承担方

    @Field("sup_name")
    private String supName; // 供应商

    private String region; // 认证区域

    @Field("cert_owner")
    private String certOwner; // 证书归属

    @Field("cert_type")
    private CertType[] certTypes; // 认证类型和方式

    @Field("jv_model")
    private String jvModel; // JV型号范围

    @Field("sup_model")
    private String supModel; // 供应商型号范围

    @Field("task_stat")
    private String taskStat; // 任务状态

    @Field("start_date")
    private Date startDate; // 开始日期

    @Field("end_date")
    private Date endDate; // 结束日期
}
