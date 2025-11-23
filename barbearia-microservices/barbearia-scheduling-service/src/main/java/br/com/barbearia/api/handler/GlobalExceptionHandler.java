package br.com.barbearia.api.handler;

import br.com.barbearia.api.exceptions.BarberUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException; // Importante para o Circuit Breaker
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Tratamento de exceções de validação (@Valid). Retorna 400 Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Error")
                .message("One or more fields failed validation.")
                .details(errors)
                .path("") // Pode ser populado se tiver acesso ao request
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Tratamento para a exceção de conflito de agendamento (BarberUnavailableException). Retorna 409 Conflict.
     */
    @ExceptionHandler(BarberUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleBarberUnavailable(BarberUnavailableException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message(ex.getMessage())
                .path("")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Tratamento para ResponseStatusException (Usado pelo Circuit Breaker/Fallback).
     * Garante que o status code original seja respeitado (ex: 503 Service Unavailable).
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = (HttpStatus) ex.getStatusCode();

        // Verifica se a mensagem de erro é a que lançamos no fallback do Circuit Breaker
        String detailMessage = ex.getReason() != null ? ex.getReason() : "Um erro ocorreu durante o processamento da requisição.";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(detailMessage)
                .path("")
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }

    // Presume que a classe ErrorResponse já está definida, por exemplo, assim:
    /*
    @lombok.Data
    @lombok.Builder
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private Integer status;
        private String error;
        private String message;
        private String path;
        private Map<String, String> details; // Opcional, usado para erros de validação
    }
    */
}