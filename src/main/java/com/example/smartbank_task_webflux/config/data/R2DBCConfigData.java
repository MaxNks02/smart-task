package com.example.smartbank_task_webflux.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "r2dbc")
public class R2DBCConfigData {

    private String driver;
    private String username;
    private String password;
    private String host;
    private Integer port;
    private String database;
    private String locale;

}
