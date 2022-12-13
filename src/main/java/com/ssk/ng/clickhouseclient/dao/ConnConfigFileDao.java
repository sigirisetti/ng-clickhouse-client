package com.ssk.ng.clickhouseclient.dao;

import com.ssk.ng.clickhouseclient.model.ClientConnectionConfig;

import java.util.List;

public interface ConnConfigFileDao {
    String saveConnConfig(List<ClientConnectionConfig> connConfigList);

    List<ClientConnectionConfig> loadConnConfig();
}
