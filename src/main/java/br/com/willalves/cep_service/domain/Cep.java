package br.com.willalves.cep_service.domain;

import lombok.Data;

@Data
public class Cep {

  private String code;
  private String street;
  private String neighborhood;
  private String city;
  private String state;
  private String ibge;
  private String gia;
  private String ddd;
  private String siafi;

}
