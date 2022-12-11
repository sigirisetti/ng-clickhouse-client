package com.ssk.ng.clickhouseclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ClickhouseDbTableSize {

    private String table;
    private String database;
    private long size;
    private long rows;
    private Date minDate;
    private Date maxDate;
    private long days;
    private long avgDailySize;

}
