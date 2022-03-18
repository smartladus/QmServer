package com.smartladu.qmserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JvCertInfo {
    @Id
    private String id;

    private String cert_no;
    private Date valid_date;
    private Date expire_date;
    private String cert_stat;
    private CertFile[] valid_files;
    private CertFile[] history_files;
}
