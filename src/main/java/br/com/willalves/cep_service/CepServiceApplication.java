package br.com.willalves.cep_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableFeignClients
@SpringBootApplication
public class CepServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CepServiceApplication.class, args);
	}

}
