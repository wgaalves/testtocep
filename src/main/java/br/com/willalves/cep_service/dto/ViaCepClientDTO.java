package br.com.willalves.cep_service.dto;

import br.com.willalves.cep_service.domain.Cep;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViaCepClientDTO {

  @JsonProperty("cep")
  private String code;

  @JsonProperty("logradouro")
  private String street;

  @JsonProperty("bairro")
  private String neighborhood;

  @JsonProperty("localidade")
  private String city;

  @JsonProperty("uf")
  private String state;

  public Cep toDomain() {
    return Cep.builder()
        .code(this.code)
        .street(this.street)
        .neighborhood(this.neighborhood)
        .city(this.city)
        .state(this.state)
        .build();
  }
}
