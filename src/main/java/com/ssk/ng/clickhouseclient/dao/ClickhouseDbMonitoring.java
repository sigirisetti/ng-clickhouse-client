package com.ssk.ng.clickhouseclient.dao;

import com.ssk.ng.clickhouseclient.model.ClickhouseDbTableSize;
import com.ssk.ng.clickhouseclient.model.ClickhouseSystemDisk;

import java.util.List;

public interface ClickhouseDbMonitoring {
    List<ClickhouseDbTableSize> getAllSystemParts();

    List<ClickhouseDbTableSize> getDatabaseParts(String database);

    List<ClickhouseSystemDisk> getSystemDisks();
}
