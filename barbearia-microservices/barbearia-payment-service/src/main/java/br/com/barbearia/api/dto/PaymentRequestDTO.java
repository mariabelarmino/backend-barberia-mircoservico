package br.com.barbearia.api.dto;

import lombok.Data; // Import do Lombok

@Data // Gera Getters, Setters, etc. automaticamente
public class PaymentRequestDTO {
    private Double valor;
    private String nomeCartao;
    private String numeroCartao;
}