package com.coco.fastpublish.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fastpublish.datasource")
public class FastPublishProperty {

    private String driverType = "mysql";

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }
}
