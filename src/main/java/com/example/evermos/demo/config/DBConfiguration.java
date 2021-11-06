package com.example.evermos.demo.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

import java.time.Duration;

@Configuration
public class DBConfiguration extends AbstractR2dbcConfiguration {
    @Value("spring.r2dbc.host")
    private String host;
    @Value("spring.r2dbc.username")
    private String username;
    @Value("spring.r2dbc.passowrd")
    private String passowrd;
    @Value("spring.r2dbc.port")
    private String port;
    @Value("spring.r2dbc.database")
    private String database;


    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions optionBuilder = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER,"mysql")
                .option(ConnectionFactoryOptions.HOST,host)
                .option(ConnectionFactoryOptions.USER,username)
                .option(ConnectionFactoryOptions.PASSWORD,passowrd)
                .option(ConnectionFactoryOptions.PORT,Integer.valueOf(port))
                .option(ConnectionFactoryOptions.DATABASE,database)
                .option(ConnectionFactoryOptions.SSL,false)
                .option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(3))
                .build();
        return ConnectionFactories.get(optionBuilder);
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}
