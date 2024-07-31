package br.com.willalves.cep_service.service;



import br.com.willalves.cep_service.client.ViacepClient;
import br.com.willalves.cep_service.domain.Cep;
import br.com.willalves.cep_service.dto.ViaCepClientDTO;
import br.com.willalves.cep_service.exception.CepNotFoundFound;
import br.com.willalves.cep_service.repository.CepDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CepServiceTest {

    @Mock
    private ViacepClient viaCepClient;

    @Mock
    private CepDao cepDao;

    @InjectMocks
    private CepService cepService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Cep createCep() {
        return Cep.builder()
                .city("Sao Carlos")
                .code("13568880")
                .state("SP")
                .neighborhood("Bevari")
                .street("Bevari Street")
                .build();
    }

    private ViaCepClientDTO createCepDTO() {
        return ViaCepClientDTO.builder()
                .city("Sao Carlos")
                .code("13568880")
                .state("SP")
                .neighborhood("Bevari")
                .street("Bevari Street")
                .build();
    }

    @Test
    void testGetCepFromClient() {
        ViaCepClientDTO cep = this.createCepDTO();
        when(viaCepClient.getCep("13568880")).thenReturn(cep);

        Cep result = cepService.getCepFromClient("13568880");

        assertNotNull(result);
        assertEquals("13568880", result.getCode());
        assertEquals("Sao Carlos", result.getCity());
        verify(viaCepClient).getCep("13568880");
    }

    @Test
    void testGetCepFromClientWhenExceptionThrown() {
        when(viaCepClient.getCep("13568880")).thenThrow(new RuntimeException("Client Error"));

        Cep result = cepService.viaCepCallback("13568880", new RuntimeException("Client Error"));

        assertNull(result);
        verify(cepDao).find("13568-880"); // Ensure that fallback method was invoked
    }

    @Test
    void testViaCepCallbackHandlesNoCause() {

        RuntimeException exception = new RuntimeException("Client Error");
        when(cepDao.find("13568-880")).thenReturn(createCep()); // Simulate that the CEP is found
        Cep result = cepService.viaCepCallback("13568880", exception);
        assertNotNull(result);
        assertEquals("13568880", result.getCode());
        verify(cepDao).find("13568-880");
    }

    @Test
    void testViaCepCallback() {
        Cep cep = createCep();
        when(cepDao.find("13568-880")).thenReturn(cep);

        Cep result = cepService.viaCepCallback("13568880", new Exception("Fallback"));

        assertNotNull(result);
        assertEquals("13568880", result.getCode());
        assertEquals("Sao Carlos", result.getCity());
        verify(cepDao).find("13568-880");
    }

    @Test
    void testViaCepCallbackWhenCepNotFound() {
        when(cepDao.find("13568-880")).thenReturn(null);

        Cep result = cepService.viaCepCallback("13568880", new Exception("Fallback"));

        assertNull(result);
    }

    @Test
    void testCreateCepWithNullCep() {
        CepNotFoundFound thrown = assertThrows(CepNotFoundFound.class, () -> {
            cepService.createCep(null);
        });
        assertEquals("Cep Não Encontrado", thrown.getMessage());
    }

    @Test
    void testCreateCepWithNullCepCode() {
        Cep cep = Cep.builder().city("Sao Carlos").state("SP").neighborhood("Bevari").street("Bevari Street").build();

        CepNotFoundFound thrown = assertThrows(CepNotFoundFound.class, () -> {
            cepService.createCep(cep);
        });
        assertEquals("Cep Não Encontrado", thrown.getMessage());
    }

    @Test
    void testCreateCepWithValidCep() {
        Cep cep = createCep();
        when(cepDao.find("13568880")).thenReturn(null);

        Cep result = cepService.createCep(cep);

        verify(cepDao).create(cep);
        assertEquals("13568880", result.getCode());
        assertEquals("Sao Carlos", result.getCity());
    }

    @Test
    void testCreateCepWithExistingCep() {

        Cep existingCep = createCep();
        Cep newCep = createCep();

        when(cepDao.find("13568880")).thenReturn(existingCep);
        Cep result = cepService.createCep(newCep);
        verify(cepDao, never()).create(newCep); // Ensure create method is not called
        verify(cepDao).update(newCep); // Ensure update method is called
        assertEquals("13568880", result.getCode());
    }

    @Test
    void testCreateCepWithDifferentExistingCep() {
        Cep cep = createCep();
        Cep existingCep = Cep.builder()
                .city("sao carlos")
                .code("13568000")
                .state("SP")
                .neighborhood("Di")
                .street("2 street")
                .build();
        when(cepDao.find("13568880")).thenReturn(existingCep);

        Cep result = cepService.createCep(cep);

        verify(cepDao).update(cep);
        assertEquals("13568880", result.getCode());
    }

    @Test
    void testPersistOrUpdateCreate() {
        Cep cep = createCep();
        when(cepDao.find("13568880")).thenReturn(null);

        cepService.persistOrUpdate(cep);

        verify(cepDao).create(cep);
    }

    @Test
    void testPersistOrUpdateUpdate() {
        Cep cep = createCep();
        Cep existingCep = createCep();
        when(cepDao.find("13568880")).thenReturn(existingCep);

        cepService.persistOrUpdate(cep);

        verify(cepDao).update(cep);
    }

    @Test
    void testPersistOrUpdateWithDifferentExistingCep() {
        Cep cep = createCep();
        Cep existingCep = Cep.builder()
                .city("Another City")
                .code("13568880")
                .state("SP")
                .neighborhood("Different Neighborhood")
                .street("Different Street")
                .build();
        when(cepDao.find("13568880")).thenReturn(existingCep);

        cepService.persistOrUpdate(cep);

        verify(cepDao).update(cep);
    }
}