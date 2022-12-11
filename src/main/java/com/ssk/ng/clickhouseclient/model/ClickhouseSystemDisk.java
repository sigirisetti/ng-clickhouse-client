package com.ssk.ng.clickhouseclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClickhouseSystemDisk {

    private String name;
    private String path;
    private long freeSpace;
    private long totalSpace;
    private long keepFreeSpace;
    private String type;
}
