package com.smarladu.qmserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Slf4j
public class PageResult<T> implements Serializable {
    private long total;
    private List<T> items;
}
