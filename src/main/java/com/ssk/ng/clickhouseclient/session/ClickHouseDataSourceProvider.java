package com.ssk.ng.clickhouseclient.session;

import com.clickhouse.jdbc.ClickHouseDataSource;
import com.ssk.ng.clickhouseclient.model.AllConnections;
import com.ssk.ng.clickhouseclient.model.ClientConnectionConfig;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssk.ng.clickhouseclient.db.ClickhouseDatasourceInitializer.createDataSource;

@Slf4j
public class ClickHouseDataSourceProvider {

    public static final String CLICKHOUSE_CLIENT_CONN_CONFIG_XML = "clickhouse-client-conn-config.xml";

    private String selectedEnv;

    private Map<String, ClickHouseDataSource> dataSourceMap = new HashMap<>();

    private Map<String, ClientConnectionConfig> connectionConfigMap = new HashMap<>();

    public ClickHouseDataSource getClickHouseDataSource() throws SQLException {
        if (dataSourceMap.containsKey(selectedEnv)) {
            return dataSourceMap.get(selectedEnv);
        }
        ClickHouseDataSource dataSource = createDataSource(connectionConfigMap.get(selectedEnv));
        dataSourceMap.put(selectedEnv, dataSource);
        log.info("Created datasource for selected env : {}", selectedEnv);
        return dataSource;
    }


    public List<ClientConnectionConfig> loadConnConfig() {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(AllConnections.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            File f = getConnConfigFilePath();
            if (!f.exists()) {
                return Collections.emptyList();
            }
            //We had written this file in marshalling example
            AllConnections emps = (AllConnections) jaxbUnmarshaller.unmarshal(getConnConfigFilePath());
            return emps.getAllConnConfigs();
        } catch (JAXBException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private File getConnConfigFilePath() {
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir, CLICKHOUSE_CLIENT_CONN_CONFIG_XML);
        if (file.exists()) {
            return file;
        }
        URL url = Thread.currentThread().getContextClassLoader().getResource(CLICKHOUSE_CLIENT_CONN_CONFIG_XML);
        return new File(url.toExternalForm());
    }

    public String getSelectedEnv() {
        return selectedEnv;
    }

    public void setSelectedEnv(String selectedEnv) {
        this.selectedEnv = selectedEnv;
    }

    public Map<String, ClickHouseDataSource> getDataSourceMap() {
        return dataSourceMap;
    }

}
