package br.com.barbearia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe global para tratamento de exceções usando @ControllerAdvice.
 * Garante que todas as respostas de erro da API sejam padronizadas (JSON).
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata a exceção BarberUnavailableException e retorna HttpStatus.CONFLICT (409).
     * Esta é a exceção de regra de negócio de agendamento.
     */
    @ExceptionHandler(BarberUnavailableException.class)
    public ResponseEntity<ErrorDetails> handleBarberUnavailableException(BarberUnavailableException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                "CONFLICT" // Código de erro customizado
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Trata a exceção MethodArgumentNotValidException (erros de validação @Valid).
     * Retorna HttpStatus.BAD_REQUEST (400).
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Erro de validação de argumentos",
                errors.toString(),
                "BAD_REQUEST"
        );

        // Embora o status seja 400, o corpo da resposta usa o formato ErrorDetails
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Trata outras exceções gerais não capturadas, garantindo um formato de erro consistente.
     * Retorna HttpStatus.INTERNAL_SERVER_ERROR (500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Um erro inesperado ocorreu no servidor.",
                request.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

/**
 * DTO para padronizar o corpo da resposta de erro (JSON).
 */
class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private String errorCode;

    public ErrorDetails(LocalDateTime timestamp, String message, String details, String errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
    }

    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getMessage() { return message; }
    public String getDetails() { return details; }
    public String getErrorCode() { return errorCode; }

    // Setters (Opcional, mas útil para frameworks de serialização)
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setMessage(String message) { this.message = message; }
    public void setDetails(String details) { this.details = details; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
}