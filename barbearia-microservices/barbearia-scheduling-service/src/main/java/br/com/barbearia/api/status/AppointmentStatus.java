package br.com.barbearia.api.domain;

/**
 * Enum que define os possíveis status de um agendamento.
 */
public enum AppointmentStatus {
    PENDING,    // Pendente (aguardando confirmação)
    CONFIRMED,  // Confirmado
    CANCELED,   // Cancelado
    COMPLETED   // Concluído (serviço realizado)
}