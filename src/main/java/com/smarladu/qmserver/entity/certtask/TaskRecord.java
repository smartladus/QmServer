package com.smarladu.qmserver.entity.certtask;

import com.alibaba.excel.annotation.ExcelProperty;
import com.smarladu.qmserver.easyexcel.converter.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "task_record")
public class TaskRecord implements Serializable {
    @Id
    private String id;

    @ExcelProperty("record_no")
    private String record_no;

    @ExcelProperty("task_no")
    private String task_no;

    @ExcelProperty("task_stat")
    private String task_stat;

    @ExcelProperty("content")
    private String content;

    @ExcelProperty(value = "record_time")
    private Date record_time;
}
