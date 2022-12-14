package com.ssk.ng.clickhouseclient.model;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement(name = "client-conn-config")
@XmlType(propOrder = {"env", "hostname", "port", "username", "password"})
public class ClientConnectionConfig {

    private String env;
    private String hostname;
    private int port;
    private String username;
    private String password;

    public ClientConnectionConfig(String env, String hostname, int port, String username, String password) {
        this.env = env;
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public ClientConnectionConfig() {
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validate() {
        return StringUtils.isBlank(env)
                && StringUtils.isNotBlank(hostname) && port > 0
                && StringUtils.isBlank(username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientConnectionConfig that = (ClientConnectionConfig) o;
        return port == that.port && Objects.equals(env, that.env) && Objects.equals(hostname, that.hostname) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(env, hostname, port, username, password);
    }

    @Override
    public String toString() {
        return env + "[ " + hostname + ":" + port + "/" + username + "]";
    }
}
