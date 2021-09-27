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
import java.lang.reflect.Field;
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
    private String recordNo;

    @ExcelProperty("task_no")
    private String taskNo;

    @ExcelProperty("task_stat")
    private String taskStat;

    @ExcelProperty("content")
    private String content;

    @ExcelProperty(value = "record_time")
    private Date recordTime;

    public static void main(String[] args) {
        String fieldName = "content";
        TaskRecord record = new TaskRecord("111", "rec_no111", "taskno111", "DONE", "内容", new Date());
        Field[] fields = record.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                System.out.println(fieldName + "->" + field.getName());
                if (fieldName.equals(field.getName())) {
                    System.out.println(field.get(record));
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
