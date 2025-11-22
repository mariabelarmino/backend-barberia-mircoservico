package br.com.barbearia.api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO de requisição para a criação de um novo Agendamento.
 */
@Data
@Builder
public class AppointmentRequestDTO {

    // O ID do cliente será pego do token de autenticação, mas
    // o ID do barbeiro precisa ser informado.
    @NotBlank(message = "O ID do barbeiro é obrigatório.")
    private String barberId;

    @NotBlank(message = "O ID da barbearia é obrigatório.")
    private String barbershopId;

    @NotBlank(message = "O ID do serviço é obrigatório.")
    private String serviceItemId;

    @NotNull(message = "A data e hora de início é obrigatória.")
    @Future(message = "A data do agendamento deve ser futura.")
    private LocalDateTime startTime;

    // Duração e preço serão calculados pelo serviço
}