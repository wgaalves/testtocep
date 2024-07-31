package br.com.willalves.cep_service.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class CepServiceExceptionHandler  extends ResponseEntityExceptionHandler {

    //@ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> exceptionHandler(Exception e) {
        e.printStackTrace();
        Map<String, String> message = Collections.singletonMap("message", e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({CepNotFoundFound.class})
    public ResponseEntity<Map<String, String>> exceptionHandlerClientException(Exception e) {
        e.printStackTrace();
        Map<String, String> message = Collections.singletonMap("message", "Endereço não encontrado");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    //@ExceptionHandler(FeignException.FeignServerException.class)
    public ResponseEntity<Map<String, String>> exceptionHandlerServerException(Exception e) {
        e.printStackTrace();
        Map<String, String> message = Collections.singletonMap("message", "Erro interno");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
