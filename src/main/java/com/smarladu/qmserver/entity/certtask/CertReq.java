package com.smarladu.qmserver.entity.certtask;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: QmServer
 * @description: 对应CertTask的cert_req一项，用于记录认证类型和获证方式
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CertReq implements Serializable {
    private String name;
    private String method;
}
