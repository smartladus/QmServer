package com.smarladu.qmserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "jv_cert_record")
public class CertRecord implements Serializable {
    @Id
    private String id;

    @Field("record_no")
    private String recNo;

    @Field("jv_cert_task_no")
    private String taskNo;


    @Field("jv_cert_task_stat")
    private String taskStat;

    @Field("record_content")
    private String recContent;

    @Field("record_time")
    private Date recTime;
}
