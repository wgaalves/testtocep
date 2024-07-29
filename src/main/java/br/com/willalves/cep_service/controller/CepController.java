package br.com.willalves.cep_service.controller;

import br.com.willalves.cep_service.controller.spec.CepSpecification;
import br.com.willalves.cep_service.dto.ViaCepClientDTO;

import br.com.willalves.cep_service.service.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@Controller
public class CepController implements CepSpecification {

    @Autowired
    private CepService cepService;

    @Override
    @GetMapping(value = "/cep/{cep}", produces = "application/json")
    public ResponseEntity<ViaCepClientDTO> getCep(@PathVariable String cep) {
        return ResponseEntity.status(HttpStatus.OK).body(cepService.getCep(cep));
    }
}
