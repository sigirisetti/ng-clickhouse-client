package com.ssk.ng.clickhouseclient.dao;

import com.ssk.ng.clickhouseclient.web.session.ClickhouseDataSourceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClickhouseClientDaoConfiguration {

    @Bean
    public ClickhouseDataSourceProvider getClickhouseDataSourceProvider(ConnConfigFileDao connConfigFileDao) {
        return new ClickhouseDataSourceProvider(connConfigFileDao);
    }

    @Bean
    public ClickhouseDbMonitoring getClickhouseDbMonitoring(ClickhouseDataSourceProvider provider) {
        return new ClickhouseDbMonitoringImpl(provider);
    }

    @Bean
    public DbMetaDataDao getDbMetaDataDao(ClickhouseDataSourceProvider provider) {
        return new DbMetaDataDaoImpl(provider);
    }

    @Bean
    public TableDaoImpl getTableDao(ClickhouseDataSourceProvider provider) {
        return new TableDaoImpl(provider);
    }

    @Bean
    public ConnConfigFileDao getConnConfigFileDao() {
        return new ConnConfigFileDaoImpl();
    }
}
