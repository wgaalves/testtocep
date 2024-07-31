package br.com.willalves.cep_service.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class WriteDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.write")
    public DataSourceProperties dataSourcePropertiesWrite() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource writeDataSource() {
        return dataSourcePropertiesWrite()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate WriteTemplate(@Qualifier("readDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
