package br.com.barbearia.api.controller;

import br.com.barbearia.api.dto.AppointmentRequestDTO;
import br.com.barbearia.api.dto.AppointmentResponseDTO;
import br.com.barbearia.api.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas a Agendamentos (Appointments).
 */
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * POST /appointments
     * Cria um novo agendamento para o cliente autenticado.
     * O ID do cliente é extraído do token de autenticação (UserDetails).
     *
     * @param request O DTO com os detalhes do agendamento (Barbeiro, Serviço, Hora de Início).
     * @param principal O objeto UserDetails do Spring Security, contendo as informações do usuário logado.
     * @return ResponseEntity com o DTO do agendamento criado e status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @Valid @RequestBody AppointmentRequestDTO request,
            @AuthenticationPrincipal UserDetails principal) {

        // Em um sistema real, o nome de usuário (getUsername) é frequentemente o ID do cliente.
        // Assumindo que o nome de usuário é o ID que precisamos para o campo customerId.
        String customerId = principal.getUsername();

        AppointmentResponseDTO createdAppointment = appointmentService.createAppointment(request, customerId);

        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    /**
     * GET /appointments
     * Lista todos os agendamentos do cliente autenticado.
     *
     * @param principal O objeto UserDetails do Spring Security.
     * @return ResponseEntity com a lista de agendamentos e status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getMyAppointments(
            @AuthenticationPrincipal UserDetails principal) {

        String customerId = principal.getUsername();
        List<AppointmentResponseDTO> appointments = appointmentService.getCustomerAppointments(customerId);

        return ResponseEntity.ok(appointments);
    }
}