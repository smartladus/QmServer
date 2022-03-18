package com.smartladu.qmserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CertFile implements Serializable {
    private String type; // 文件类型，cert或者report
    private String file_name; // 文件名
    private String file_path; // 文件存储路径
    private Date time_stamp; // 时间戳
}
