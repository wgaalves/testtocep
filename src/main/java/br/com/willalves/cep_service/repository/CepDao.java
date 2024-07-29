package br.com.willalves.cep_service.repository;

import br.com.willalves.cep_service.domain.Cep;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Log
@AllArgsConstructor
@Component
public class CepDao {

 JdbcTemplate jdbcTemplate;

  public Cep find(String code) {
    String SQL_RETRIEVE_CEP = "select * from cep where code = ?";
    return jdbcTemplate.query(SQL_RETRIEVE_CEP, new Object[] {code}, rs -> {
        if (rs.next()) {
            Cep cep = new Cep();
            cep.setCode(rs.getString("code"));
            cep.setStreet(rs.getString("street"));
            cep.setNeighborhood(rs.getString("neighborhood"));
            cep.setCity(rs.getString("city"));
            cep.setState(rs.getString("state"));
            cep.setDdd(rs.getString("ddd"));
            cep.setGia(rs.getString("gia"));
            cep.setIbge(rs.getString("ibge"));
            cep.setSiafi(rs.getString("siafi"));

            return cep;
        }else {
            System.out.println("nulo");
            return null;
        }
    });
  }

  public boolean create(Cep cep) {
    String SQL_INSERT_CEP = "insert into cep(code, street, neighborhood, city, state, ibge, gia, ddd, siafi) values(?,?,?,?,?,?,?,?,?)";
    return jdbcTemplate.update(SQL_INSERT_CEP,
                                cep.getCode(),
                                cep.getStreet(),
                                cep.getNeighborhood(),
                                cep.getCity(),
                                cep.getState(),
                                cep.getIbge(),
                                cep.getGia(),
                                cep.getDdd(),
                                cep.getSiafi()) > 0;
  }

  public boolean update(Cep cep){
      String SQL_UPDATE_CEP = "update cep set street = ?, neighborhood = ?, city = ?, state = ?, ibge = ?, gia = ?, ddd = ?, siafi= ? where code = ?";

      return jdbcTemplate.update(SQL_UPDATE_CEP,
              cep.getStreet(),
              cep.getNeighborhood(),
              cep.getCity(),
              cep.getState(),
              cep.getIbge(),
              cep.getGia(),
              cep.getDdd(),
              cep.getSiafi(),
              cep.getCode()) > 0;
  }
}