package br.com.barbearia.api.repository;

import br.com.barbearia.api.domain.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para operações CRUD e consultas personalizadas na coleção 'appointments'.
 */
@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    /**
     * Encontra todos os agendamentos para um barbeiro específico em um período de tempo.
     * Isso será crucial para verificar a disponibilidade.
     */
    List<Appointment> findByBarberIdAndStartTimeBetween(String barberId, LocalDateTime start, LocalDateTime end);

    /**
     * Encontra agendamentos por cliente.
     */
    List<Appointment> findByCustomerId(String customerId);

    /**
     * Encontra agendamentos por ID da barbearia.
     */
    List<Appointment> findByBarbershopId(String barbershopId);
}