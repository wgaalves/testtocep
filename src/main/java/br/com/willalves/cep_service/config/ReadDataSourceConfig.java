package br.com.willalves.cep_service.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@AllArgsConstructor
@Configuration
public class ReadDataSourceConfig {


    @Bean
    @ConfigurationProperties("spring.datasource.read")
    public DataSourceProperties dataSourcePropertiesRead() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource readDataSource() {
        return dataSourcePropertiesRead()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate readTemplate(@Qualifier("writeDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
