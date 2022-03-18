package com.smartladu.qmserver.entity.certtask;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2022/3/11
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cert_task_stat")
public class CertTaskStat {
    @Id
    private String id;

    @ExcelProperty("state")
    private String state;

    @ExcelProperty("label")
    private String label;

    @ExcelProperty("type")
    private String type;
}
