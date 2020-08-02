package com.lass.products.mqtt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lass.mqtt")
public class MQTTConfigurationProperties {
    private String host;
    private Integer port;
    private String clientId;
    private Boolean persistence;

    public MQTTConfigurationProperties() {
    }

    public MQTTConfigurationProperties(String host, Integer port, String clientId,Boolean persistence) {
        this.host = host;
        this.port = port;
        this.clientId = clientId;
        this.persistence = persistence;
    }

    public Boolean getPersistence() {
        return persistence;
    }

    public void setPersistence(Boolean persistence) {
        this.persistence = persistence;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
