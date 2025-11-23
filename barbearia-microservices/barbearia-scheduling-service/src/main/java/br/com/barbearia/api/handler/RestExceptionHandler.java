package br.com.barbearia.api.exception.handler;

import br.com.barbearia.api.dto.response.ErrorDetails;
import br.com.barbearia.api.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Componente @ControllerAdvice para lidar globalmente com exceções RESTful.
 * Garante respostas de erro padronizadas (e.g., 404, 400, 500).
 */
@ControllerAdvice
@Slf4j // Para registrar os erros que ocorrerem
public class RestExceptionHandler {

    /**
     * Trata a ResourceNotFoundException e a mapeia para o status HTTP 404 Not Found.
     *
     * @param ex A exceção lançada pelo Service.
     * @param request O objeto de requisição HTTP.
     * @return Uma ResponseEntity com o DTO de erro e status 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {

        String path = request.getRequestURI();
        HttpStatus status = HttpStatus.NOT_FOUND;

        log.warn("Recurso não encontrado. Status: {}, Path: {}, Erro: {}", status.value(), path, ex.getMessage());

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message("Recurso não encontrado.")
                .details(ex.getMessage()) // Mensagem detalhada sobre o que não foi encontrado
                .path(path)
                .build();

        return new ResponseEntity<>(errorDetails, status);
    }

    // Você pode adicionar mais métodos @ExceptionHandler aqui para tratar 400 Bad Request, 500 Internal Server Error, etc.
}