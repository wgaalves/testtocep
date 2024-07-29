package br.com.willalves.cep_service.service;

import br.com.willalves.cep_service.client.ViacepClient;
import br.com.willalves.cep_service.domain.Cep;
import br.com.willalves.cep_service.dto.ViaCepClientDTO;
import br.com.willalves.cep_service.repository.CepDao;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Log
@Service
@RequiredArgsConstructor
public class CepService {

  private final ViacepClient viaCepClient;

  private final CepDao dao;


  @CircuitBreaker(name = "cep", fallbackMethod = "fallback")
  public ViaCepClientDTO getCepFromClient(String cep) {
    return viaCepClient.getCep(cep);
  }

  public ViaCepClientDTO fallback(String cep, Exception e) {
    return parseDomainToDto(getCepOnDB(cep.substring(0, 5)+ "-" + cep.substring(5, 8)));
  }

  @Cacheable(value = "itemCache", sync = true)
  public ViaCepClientDTO getCep(String cep) {
    ViaCepClientDTO response = getCepFromClient(cep);
    persistOrUpdate(response);
    return response;
  }

  public void create(Cep cep) {
    dao.create(cep);
  }

  public void update(Cep cep) {
    dao.update(cep);
  }

  public Cep getCepOnDB(String cep) {
    return dao.find(cep);
  }

  private void persistOrUpdate(ViaCepClientDTO cep) {

    Cep query = getCepOnDB(cep.getCode());
    if (query == null) {
      create(parseDtotoDomain(cep));
    } else if (!query.equals(parseDtotoDomain(cep))) {
      update(parseDtotoDomain(cep));
    }
  }

  private Cep parseDtotoDomain(ViaCepClientDTO cep) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(cep, Cep.class);
  }
  private ViaCepClientDTO parseDomainToDto(Cep cep) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(cep, ViaCepClientDTO.class);
  }

}
