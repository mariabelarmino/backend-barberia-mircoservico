package br.com.barbearia.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO de resposta para a entidade Barbeiro.
 */
@Data
@Builder
public class BarberResponseDTO {
    private String id;
    private String userId; // ID do usuário no Auth Service
    private String name;
    private List<String> barbershopIds;
    private String bio;
}