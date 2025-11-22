package br.com.barbearia.api.dto;

import br.com.barbearia.api.domain.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO de resposta para a entidade Agendamento.
 */
@Data
@Builder
public class AppointmentResponseDTO {
    private String id;
    private String barberId;
    private String barbershopId;
    private String customerId;
    private String serviceItemId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int durationMinutes;
    private double price;
    private AppointmentStatus status;
}