package br.com.barbearia.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

/**
 * DTO para representar um item de serviço (Corte, Barba, etc.).
 * Usado tanto para entrada de dados (Request) quanto para saída (Response).
 */
@Value
@Builder
public class ServiceItemDTO {

    // ID do serviço no MongoDB. Não é obrigatório na criação.
    String id;

    @NotBlank(message = "O nome do serviço é obrigatório.")
    String name;

    @NotBlank(message = "A descrição do serviço é obrigatória.")
    String description;

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    BigDecimal price;

    @NotNull(message = "A duração em minutos é obrigatória.")
    @Min(value = 5, message = "A duração mínima do serviço é de 5 minutos.")
    Integer durationMinutes;
}