package com.ssk.ng.clickhouseclient.db;

import com.clickhouse.jdbc.ClickHouseDataSource;
import com.ssk.ng.clickhouseclient.model.ClientConnectionConfig;
import ru.yandex.clickhouse.settings.ClickHouseConnectionSettings;

import java.sql.SQLException;
import java.util.Properties;

public class ClickhouseDatasourceInitializer {
    public static ClickHouseDataSource createDataSource(ClientConnectionConfig connectionConfig) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", connectionConfig.getUsername());
        properties.setProperty("password", connectionConfig.getPassword());
        properties.setProperty(ClickHouseConnectionSettings.CLIENT_NAME.getKey(), "ng-clickhouse-client");
        properties.setProperty(ClickHouseConnectionSettings.USE_TIME_ZONE.getKey(), "Asia/Singapore");
        String url = String.format("jdbc:clickhouse://%s:%s",
                connectionConfig.getHostname(), connectionConfig.getPort());
        return new ClickHouseDataSource(url, properties);
    }
}