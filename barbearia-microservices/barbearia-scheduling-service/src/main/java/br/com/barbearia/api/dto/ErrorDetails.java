package br.com.barbearia.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para padronizar a resposta de erro da API.
 * Garante que todos os erros retornados (e.g., 404, 400) tenham um formato consistente.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private String path; // O URI da requisição que causou o erro
    private int status; // Código HTTP do erro
}