package br.com.willalves.cep_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Autowired
    Environment environment;

    @Bean
    DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("DB_URL"));
        dataSource.setUsername(environment.getProperty("DB_USERNAME"));
        dataSource.setPassword(environment.getProperty("DB_PASSWORD"));
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }
}
