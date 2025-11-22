package br.com.barbearia.api.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalTime;
import java.util.List;

/**
 * DTO usado para retornar os dados de uma Barbearia nas respostas da API.
 * Não inclui campos internos como ownerId, focando nos dados de visualização pública.
 */
@Value
@Builder
public class BarbershopResponseDTO {

    String id;
    String name;
    String address;
    String phone;
    String email;
    String description;
    LocalTime openingTime;
    LocalTime closingTime;
    List<ServiceItemDTO> services;

    // Proprietário da barbearia. Importante para fins de administração.
    String ownerId;
}