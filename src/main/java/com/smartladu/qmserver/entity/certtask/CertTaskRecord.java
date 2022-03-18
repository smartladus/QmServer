package com.smartladu.qmserver.entity.certtask;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Document(collection = "cert_task_record")
public class CertTaskRecord implements Serializable {
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

    @ExcelProperty("record_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;

    public static void main(String[] args) {
        String fieldName = "content";
        CertTaskRecord record = new CertTaskRecord("111", "rec_no111", "taskno111", "DONE", "内容", new Date());
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
