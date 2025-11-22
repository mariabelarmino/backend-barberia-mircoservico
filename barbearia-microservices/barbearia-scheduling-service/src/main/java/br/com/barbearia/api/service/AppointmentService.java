package br.com.barbearia.api.service;

import br.com.barbearia.api.client.BarbershopServiceClient;
import br.com.barbearia.api.domain.Appointment;
import br.com.barbearia.api.domain.AppointmentStatus;
import br.com.barbearia.api.dto.AppointmentRequestDTO;
import br.com.barbearia.api.dto.AppointmentResponseDTO;
import br.com.barbearia.api.dto.ServiceDetailsDTO;
import br.com.barbearia.api.exception.BarberUnavailableException; // Importando a nova exceção
import br.com.barbearia.api.repository.AppointmentRepository;
import br.com.barbearia.api.repository.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio dos Agendamentos.
 * Inclui:
 * 1. Validação de disponibilidade do Barbeiro.
 * 2. Integração com o Barbershop-Service para obter detalhes do serviço (preço/duração).
 */
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final BarbershopServiceClient barbershopServiceClient; // Cliente de Microsserviço Injetado

    @Autowired
    public AppointmentService(
            AppointmentRepository appointmentRepository,
            BarberRepository barberRepository,
            BarbershopServiceClient barbershopServiceClient) {
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
        this.barbershopServiceClient = barbershopServiceClient;
    }

    /**
     * Cria um novo agendamento.
     * 1. Busca detalhes do serviço no Barbershop-Service (preço e duração).
     * 2. Calcula o horário de término.
     * 3. Verifica a disponibilidade do barbeiro.
     * 4. Salva o agendamento.
     *
     * @param request O DTO com os dados de agendamento.
     * @param customerId O ID do cliente autenticado.
     * @return O DTO de resposta do agendamento criado.
     */
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO request, String customerId) {

        // 1. CHAMA BARBERSHOP SERVICE: Buscar o ServiceItem para obter preço e duração.
        ServiceDetailsDTO serviceDetails = barbershopServiceClient.getServiceDetails(request.getServiceItemId());

        // 2. Calcular o horário de término baseado na DURAÇÃO REAL
        LocalDateTime endTime = request.getStartTime().plusMinutes(serviceDetails.getDurationMinutes());

        // 3. Verificar Disponibilidade do Barbeiro
        if (!isBarberAvailable(request.getBarberId(), request.getStartTime(), endTime)) {
            // Lança a exceção customizada, que será tratada pelo GlobalExceptionHandler
            throw new BarberUnavailableException("O barbeiro não está disponível no horário solicitado.");
        }

        // 4. Criar a Entidade de Agendamento com dados reais
        Appointment appointment = Appointment.builder()
                .barberId(request.getBarberId())
                .barbershopId(request.getBarbershopId())
                .customerId(customerId)
                .serviceItemId(request.getServiceItemId())
                .startTime(request.getStartTime())
                .endTime(endTime)
                .durationMinutes(serviceDetails.getDurationMinutes())
                .price(serviceDetails.getPrice().doubleValue()) // Usando doubleValue para simplificar o DTO
                .status(AppointmentStatus.PENDING)
                .build();

        // 5. Salvar e Mapear
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToDTO(savedAppointment);
    }

    /**
     * Busca todos os agendamentos de um cliente.
     * @param customerId ID do cliente.
     * @return Lista de DTOs de agendamento.
     */
    public List<AppointmentResponseDTO> getCustomerAppointments(String customerId) {
        return appointmentRepository.findByCustomerId(customerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lógica para verificar se o barbeiro tem horários conflitantes.
     * @param barberId ID do barbeiro.
     * @param newStart Hora de início do novo agendamento.
     * @param newEnd Hora de término do novo agendamento.
     * @return true se estiver disponível, false caso contrário.
     */
    private boolean isBarberAvailable(String barberId, LocalDateTime newStart, LocalDateTime newEnd) {
        // Busca agendamentos que caem no período [newStart - 1 minuto, newEnd + 1 minuto]
        List<Appointment> overlappingAppointments = appointmentRepository
                .findByBarberIdAndStartTimeBetween(barberId, newStart.minusMinutes(1), newEnd.plusMinutes(1))
                .stream()
                // Filtra apenas agendamentos ATIVOS que realmente se sobrepõem
                .filter(a -> a.getStatus() == AppointmentStatus.CONFIRMED || a.getStatus() == AppointmentStatus.PENDING)
                .filter(a -> a.getStartTime().isBefore(newEnd) && a.getEndTime().isAfter(newStart))
                .collect(Collectors.toList());

        return overlappingAppointments.isEmpty();
    }

    /**
     * Converte a entidade Appointment para o DTO de resposta.
     */
    private AppointmentResponseDTO mapToDTO(Appointment appointment) {
        return AppointmentResponseDTO.builder()
                .id(appointment.getId())
                .barberId(appointment.getBarberId())
                .barbershopId(appointment.getBarbershopId())
                .customerId(appointment.getCustomerId())
                .serviceItemId(appointment.getServiceItemId())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .durationMinutes(appointment.getDurationMinutes())
                .price(appointment.getPrice())
                .status(appointment.getStatus())
                .build();
    }
}