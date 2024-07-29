package br.com.willalves.cep_service.client;

import br.com.willalves.cep_service.dto.ViaCepClientDTO;
import br.com.willalves.cep_service.service.CepService;
import feign.Param;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "catalogRestClient", url = "http://viacep.com.br")
public interface ViacepClient {



  @GetMapping(value = "/ws/{cep}/json/")
  ViaCepClientDTO getCep(@PathVariable String cep);
}
