package com.smarladu.qmserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CertInfo {
    private String certName;
    private Date validDate;
    private Date expireDate;
    private String certStat;
    private String filePath;
}
