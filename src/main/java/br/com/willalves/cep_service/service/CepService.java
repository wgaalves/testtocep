package br.com.willalves.cep_service.service;

import br.com.willalves.cep_service.client.ViacepClient;
import br.com.willalves.cep_service.domain.Cep;
import br.com.willalves.cep_service.dto.ViaCepClientDTO;
import br.com.willalves.cep_service.exception.exceptionhandler.CepNotFoundFound;
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


  @CircuitBreaker(name = "circuitViacep", fallbackMethod = "viaCepCallback")
  public Cep getCepFromClient(String cep) {
    return viaCepClient.getCep(cep).toDomain();
  }

  public Cep viaCepCallback(String cep, Exception e){
    log.info(e.getMessage());
    log.info(e.getCause().toString());
    return getCepOnDB(cep.substring(0, 5) + "-" + cep.substring(5, 8));
  }

  @Cacheable(value = "itemCache", sync = true)
  public Cep createCep(Cep cep) {

    if (cep == null || cep.getCode() == null) {
      throw new CepNotFoundFound("Cep Não Encontrado");
    }
    persistOrUpdate(cep);
    return cep;
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

  private void persistOrUpdate(Cep cep) {

    Cep query = getCepOnDB(cep.getCode());
    if (query == null) {
      create(cep);
    } else if (!query.equals(cep)) {
      update(cep);
    }
  }

}
