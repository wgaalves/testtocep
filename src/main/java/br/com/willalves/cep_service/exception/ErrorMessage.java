package br.com.willalves.cep_service.exception;


import br.com.willalves.cep_service.enumerator.ErrorENUM;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private String link;
    private HttpStatus status;

    private List<String> errors = new ArrayList<>();
    private List<ErrorMessage> problemObjects = new ArrayList<>();

    public ErrorMessage(final String msg, final ErrorENUM errorId) {
        this.message = msg;
    }

    public ErrorMessage(final String msg) {
        this.message = msg;
    }

    public ErrorMessage(final String name, final String message, final ErrorENUM errorId) {
        this.message = message;
    }

    public ErrorMessage(final String msg, final List<String> errors) {
        this.message = msg;
        this.errors = errors;
    }

    public ErrorMessage(final String msg, final List<String> errors, final String link) {
        this.message = msg;
        this.errors = errors;
        this.link = link;
    }

    public <T> ErrorMessage(final String msg, final Set<ConstraintViolation<T>> violations) {
        this.message = msg;
        this.errors = new ArrayList<>();

        violations.forEach(violation -> errors.add(violation.getMessage()));
    }

    public ErrorMessage(HttpStatus status, ErrorENUM problemType, String detail) {
        this.status = status;
        this.message = detail;
    }

    public ErrorMessage(HttpStatus status, ErrorENUM problemType, String detail, List<ErrorMessage> problemObjects) {
        this.status = status;
        this.message = detail;
        this.problemObjects = problemObjects;
    }

    public void addError(final String error) {
        errors.add(error);
    }

}