package com.gps.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 命令响应数据实体类
 */
@Data
@AllArgsConstructor
public class RecordData {

    private int totalPac;
    private int indexPac;
    private String data;//900
    private int len;
    private Date time;

}
