package com.ssk.ng.clickhouseclient.web.session;

import com.clickhouse.jdbc.ClickHouseDataSource;
import com.ssk.ng.clickhouseclient.dao.ConnConfigFileDao;
import com.ssk.ng.clickhouseclient.model.ClientConnectionConfig;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.ssk.ng.clickhouseclient.db.ClickhouseDatasourceInitializer.createDataSource;

@Slf4j
public class ClickhouseDataSourceProvider {

    private final ConnConfigFileDao connConfigFileDao;
    private ClientConnectionConfig selectedEnv;

    private Map<ClientConnectionConfig, ClickHouseDataSource> dataSourceMap = new HashMap<>();

    public ClickhouseDataSourceProvider(ConnConfigFileDao connConfigFileDao) {
        log.info("Created ClickHouseDataSourceProvider");
        this.connConfigFileDao=connConfigFileDao;

    }

    public ClickHouseDataSource getClickHouseDataSource() throws SQLException {
        if (dataSourceMap.containsKey(selectedEnv)) {
            return dataSourceMap.get(selectedEnv);
        }
        ClickHouseDataSource dataSource = createDataSource(selectedEnv);
        dataSourceMap.put(selectedEnv, dataSource);
        log.info("Created datasource for selected env : {}", selectedEnv);
        return dataSource;
    }

    public ClientConnectionConfig getSelectedEnv() {
        return selectedEnv;
    }

    public void setSelectedEnv(ClientConnectionConfig selectedEnv) {
        this.selectedEnv = selectedEnv;
    }

    public Map<ClientConnectionConfig, ClickHouseDataSource> getDataSourceMap() {
        return dataSourceMap;
    }

}
