package com.ssk.ng.clickhouseclient.dao;

import com.ssk.ng.clickhouseclient.model.AllConnections;
import com.ssk.ng.clickhouseclient.model.ClientConnectionConfig;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class ConnConfigFileDaoImpl implements ConnConfigFileDao {

    public static final String CLICKHOUSE_CLIENT_CONN_CONFIG_XML = "clickhouse-client-conn-config.xml";

    private JFrame parent;

    @Override
    public String saveConnConfig(List<ClientConnectionConfig> connConfigList) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(AllConnections.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            AllConnections allConnections = new AllConnections();
            allConnections.setAllConnConfigs(connConfigList);

            jaxbMarshaller.marshal(allConnections, System.out);

            jaxbMarshaller.marshal(allConnections, getConnConfigFilePath());
            return null;
        } catch (JAXBException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
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
}
