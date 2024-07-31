package br.com.willalves.cep_service.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@AllArgsConstructor
@Configuration
public class ReadDataSourceConfig {


    @Bean
    @ConfigurationProperties("spring.datasource.read")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource readDataSource() {
        return dataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }
}
