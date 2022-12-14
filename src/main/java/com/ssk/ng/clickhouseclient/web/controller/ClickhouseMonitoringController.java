package com.ssk.ng.clickhouseclient.web.controller;

import com.ssk.ng.clickhouseclient.dao.ClickhouseDbMonitoring;
import com.ssk.ng.clickhouseclient.model.ClickhouseDbTableSize;
import com.ssk.ng.clickhouseclient.model.ClickhouseSystemDisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClickhouseMonitoringController {

    @Autowired
    private ClickhouseDbMonitoring dbMonitoring;

    @GetMapping("/clickhouse/dbTableSizes")
    public List<ClickhouseDbTableSize> getClickhouseDbTableSize(@RequestParam boolean system, @RequestParam boolean pae) {
        List<ClickhouseDbTableSize> result = dbMonitoring.getAllSystemParts();
        return result;
    }

    @GetMapping("/clickhouse/getSystemDisks")
    public List<ClickhouseSystemDisk> getSystemDisks() {
        return dbMonitoring.getSystemDisks();
    }
}
