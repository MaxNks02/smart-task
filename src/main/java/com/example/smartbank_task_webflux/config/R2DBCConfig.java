package com.example.smartbank_task_webflux.config;

import com.example.smartbank_task_webflux.config.data.R2DBCConfigData;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@Configuration
@EnableR2dbcRepositories
public class R2DBCConfig extends AbstractR2dbcConfiguration {

    private final R2DBCConfigData r2DBCConfigData;

    public R2DBCConfig(R2DBCConfigData r2DBCConfigData) {
        this.r2DBCConfigData = r2DBCConfigData;
    }

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {

        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, r2DBCConfigData.getDriver())
                .option(ConnectionFactoryOptions.HOST, r2DBCConfigData.getHost())
                .option(ConnectionFactoryOptions.PORT, r2DBCConfigData.getPort())
                .option(ConnectionFactoryOptions.DATABASE, r2DBCConfigData.getDatabase())
                .option(ConnectionFactoryOptions.USER, r2DBCConfigData.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, r2DBCConfigData.getPassword())
                .option(Option.valueOf("locale"), r2DBCConfigData.getLocale())
                .build();

        return ConnectionFactories.get(options);
    }


}
