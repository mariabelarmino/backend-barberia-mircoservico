package br.com.barbearia.api.dto;

import lombok.Data; // Import do Lombok
import java.time.LocalDateTime;

@Data // Gera Getters, Setters, etc. automaticamente
public class PaymentResponseDTO {
    private String id;
    private String status;
    private LocalDateTime dataHora;
}