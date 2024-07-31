package br.com.willalves.cep_service.controller;

import br.com.willalves.cep_service.domain.Cep;
import br.com.willalves.cep_service.dto.ViaCepClientDTO;
import br.com.willalves.cep_service.service.CepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CepControllerTest {

    @InjectMocks
    private CepController cepController;

    @Mock
    private CepService cepService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCep_ValidCep() {

        String validCep = "12345678";
        Cep mockCep = mock(Cep.class);
        ViaCepClientDTO mockDto = new ViaCepClientDTO();
        when(cepService.getCepFromClient(validCep)).thenReturn(mockCep);
        when(mockCep.toDTO()).thenReturn(mockDto);

        // Act
        ResponseEntity<ViaCepClientDTO> response = cepController.getCep(validCep);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDto, response.getBody());
        verify(cepService, times(1)).getCepFromClient(validCep);
        verify(cepService, times(1)).createCep(mockCep);
    }

    @Test
    public void testGetCep_InvalidCep() {

        String invalidCep = "invalid";
        when(cepService.getCepFromClient(anyString())).thenThrow(new IllegalArgumentException("Invalid CEP"));

        try {
            cepController.getCep(invalidCep);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid CEP", e.getMessage());
        }
        verify(cepService, times(1)).getCepFromClient(invalidCep);
        verify(cepService, times(0)).createCep(any(Cep.class));
    }
}