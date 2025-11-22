package br.com.barbearia.api.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Entidade de Domínio para um Agendamento (Appointment).
 * Representa um registro de um horário marcado entre um cliente e um barbeiro.
 */
@Data
@Builder
@Document(collection = "appointments")
public class Appointment {

    // ID gerado pelo MongoDB
    @Id
    private String id;

    // ID do Barbeiro responsável pelo agendamento
    private String barberId;

    // ID da Barbearia onde o serviço será realizado (referência ao barbershop-service)
    private String barbershopId;

    // ID do Cliente que fez o agendamento (referência ao auth-service/usuário)
    private String customerId;

    // ID do Serviço agendado (referência ao item de serviço dentro do barbershop-service)
    private String serviceItemId;

    // Data e hora de início do agendamento
    private LocalDateTime startTime;

    // Data e hora de término do agendamento
    private LocalDateTime endTime;

    // Duração do serviço em minutos
    private int durationMinutes;

    // Preço total do serviço no momento do agendamento
    private double price;

    // Status do agendamento (PENDING, CONFIRMED, CANCELED, COMPLETED)
    private AppointmentStatus status;
}