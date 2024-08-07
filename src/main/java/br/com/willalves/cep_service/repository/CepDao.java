package br.com.willalves.cep_service.repository;

import br.com.willalves.cep_service.domain.Cep;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CepDao {

  @Qualifier("WriteTemplate")
  @NonNull
  private final JdbcTemplate jdbcTemplate;

  @Qualifier("readTemplate")
  @NonNull
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public Cep find(String code) {
    String SQL_RETRIEVE_CEP = "select * from cep where code = :code";

    return namedParameterJdbcTemplate.query(
        SQL_RETRIEVE_CEP,
        new MapSqlParameterSource("code", code),
        resultSet -> {
          if (resultSet.next()) {
            Cep cep = new Cep();
            return Cep.builder()
                .code(resultSet.getString("code"))
                .city(resultSet.getString("city"))
                .street(resultSet.getString("street"))
                .neighborhood("neighborhood")
                .state("state")
                .build();

          } else {
            System.out.println("nulo");
            return null;
          }
        });
  }
  ;

  public boolean create(Cep cep) {
    String SQL_INSERT_CEP =
        "insert into cep(code, street, neighborhood, city, state) values(?,?,?,?,?)";
    return jdbcTemplate.update(
            SQL_INSERT_CEP,
            cep.getCode(),
            cep.getStreet(),
            cep.getNeighborhood(),
            cep.getCity(),
            cep.getState())
        > 0;
  }

  public boolean update(Cep cep) {
    String SQL_UPDATE_CEP =
        "update cep set street = ?, neighborhood = ?, city = ?, state = ? where code = ?";

    return jdbcTemplate.update(
            SQL_UPDATE_CEP,
            cep.getStreet(),
            cep.getNeighborhood(),
            cep.getCity(),
            cep.getState(),
            cep.getCode())
        > 0;
  }
}
