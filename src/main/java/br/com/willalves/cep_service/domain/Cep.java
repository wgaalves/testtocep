package br.com.willalves.cep_service.domain;

import br.com.willalves.cep_service.dto.ViaCepClientDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cep {

  private String code;
  private String street;
  private String neighborhood;
  private String city;
  private String state;



  public ViaCepClientDTO toDTO(){
    return ViaCepClientDTO.builder()
            .code(this.code)
            .street(this.street)
            .neighborhood(this.neighborhood)
            .city(this.city)
            .state(this.state)
            .build();
  }

}
