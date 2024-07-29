package br.com.willalves.cep_service.controller.spec;

import br.com.willalves.cep_service.dto.ViaCepClientDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;


@Tag(description = "Busca de ceps", name = "CEP")
public interface CepSpecification {

    @Operation(description = "Busca de Cep")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Unexpected Error")
    })
    ResponseEntity<ViaCepClientDTO> getCep(String cep);

}
