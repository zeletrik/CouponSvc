package hu.zeletrik.couponsvc.api.error;

import hu.zeletrik.couponsvc.service.exception.NonExistingTerritoryException;
import hu.zeletrik.couponsvc.service.exception.TicketAlreadyExistException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toArray(String[]::new);
        final var body = createErrorBody(errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(value = TicketAlreadyExistException.class)
    protected ResponseEntity<Object> handleTicketAlreadyExists(RuntimeException ex) {
        final var body = createErrorBody(ex.getMessage());
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(value = NonExistingTerritoryException.class)
    protected ResponseEntity<Object> handleTerritoryNotExists(RuntimeException ex) {
        final var body = createErrorBody(ex.getMessage());
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    private Map<String, Object> createErrorBody(String... message) {
        final Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("errors", message);
        return body;
    }
}
