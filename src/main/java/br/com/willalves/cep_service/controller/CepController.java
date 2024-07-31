package br.com.willalves.cep_service.controller;

import br.com.willalves.cep_service.controller.spec.CepSpecification;
import br.com.willalves.cep_service.domain.Cep;
import br.com.willalves.cep_service.dto.ViaCepClientDTO;

import br.com.willalves.cep_service.service.CepService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class CepController implements CepSpecification {


    private final CepService cepService;

    @Cacheable(value = "itemCache", sync = true)
    @Override
    @GetMapping(value = "/cep/{cep}", produces = "application/json")
    public ResponseEntity<ViaCepClientDTO> getCep(@PathVariable String cep) {
        Cep response = cepService.getCepFromClient(cep);
        cepService.createCep(response);
        return ResponseEntity.status(HttpStatus.OK).body(response.toDTO());
    }
}
