package com.ssk.ng.clickhouseclient.web.controller;

import com.ssk.ng.clickhouseclient.dao.ConnConfigFileDao;
import com.ssk.ng.clickhouseclient.model.ClientConnectionConfig;
import com.ssk.ng.clickhouseclient.web.session.ClickhouseDataSourceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ClickhouseEnvSelectionController {

    @Autowired
    private ClickhouseDataSourceProvider clickhouseDataSourceProvider;

    @Autowired
    private ConnConfigFileDao connConfigFileDao;

    @PutMapping("/setSelectedEnv")
    public ResponseEntity<?> setSelectedEnv(@RequestBody ClientConnectionConfig selectedEnv) {
        clickhouseDataSourceProvider.setSelectedEnv(selectedEnv);
        log.info("Selected clickhouse env : {}", clickhouseDataSourceProvider.getSelectedEnv());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chConnConfig")
    public List<ClientConnectionConfig> getClientConnectionConfig() {
        return connConfigFileDao.loadConnConfig();
    }
}
