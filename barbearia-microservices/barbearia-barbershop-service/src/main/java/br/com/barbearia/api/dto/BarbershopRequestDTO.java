package br.com.barbearia.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.LocalTime;
import java.util.List;

/**
 * DTO para receber dados na criação ou atualização de uma Barbearia.
 * Inclui validações para garantir a integridade dos dados de entrada.
 */
@Value
public class BarbershopRequestDTO {

    @NotBlank(message = "O nome da barbearia é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    String name;

    @NotBlank(message = "O endereço é obrigatório.")
    String address;

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "O telefone deve estar no formato (XX) XXXX-XXXXX.")
    String phone;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O formato do email é inválido.")
    String email;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    String description;

    @NotNull(message = "O horário de abertura é obrigatório.")
    LocalTime openingTime;

    @NotNull(message = "O horário de fechamento é obrigatório.")
    LocalTime closingTime;

    @NotEmpty(message = "A barbearia deve oferecer pelo menos um serviço.")
    @Valid // Garante que as validações dentro de ServiceItemDTO sejam executadas
    List<ServiceItemDTO> services;
}