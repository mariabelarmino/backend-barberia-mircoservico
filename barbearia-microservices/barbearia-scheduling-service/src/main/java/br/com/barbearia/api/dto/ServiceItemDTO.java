package br.com.barbearia.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * DTO de Resposta para os Itens de Serviço (Corte, Barba, etc.).
 * Esta é a representação que será enviada ao cliente através da API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItemDTO {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Duration duration;
    private String barbershopId;
}