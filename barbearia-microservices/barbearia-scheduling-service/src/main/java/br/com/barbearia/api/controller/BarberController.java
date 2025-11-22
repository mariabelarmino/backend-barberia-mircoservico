package br.com.barbearia.api.controller;

import br.com.barbearia.api.dto.BarberResponseDTO;
import br.com.barbearia.api.service.BarberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Controlador REST para gerenciar e listar informações sobre Barbeiros.
 * As rotas são focadas em consultas públicas (para o cliente ver quem pode agendar).
 */
@RestController
@RequestMapping("/barbers")
public class BarberController {

    private final BarberService barberService;

    @Autowired
    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    /**
     * GET /barbers
     * Lista todos os barbeiros cadastrados no sistema.
     * @return Uma lista de DTOs de resposta dos barbeiros.
     */
    @GetMapping
    public ResponseEntity<List<BarberResponseDTO>> getAllBarbers() {
        List<BarberResponseDTO> barbers = barberService.findAllBarbers();
        return ResponseEntity.ok(barbers);
    }

    /**
     * GET /barbers/{id}
     * Busca um barbeiro específico pelo seu ID (ID do Mongo).
     * @param id O ID do barbeiro.
     * @return O DTO de resposta do barbeiro.
     * @throws ResponseStatusException se o barbeiro não for encontrado (404 Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<BarberResponseDTO> getBarberById(@PathVariable String id) {
        return barberService.findBarberById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbeiro não encontrado com o ID: " + id));
    }

    /**
     * GET /barbers/barbershop/{barbershopId}
     * Lista os barbeiros associados a uma barbearia específica.
     * @param barbershopId O ID da barbearia.
     * @return Uma lista de DTOs de resposta dos barbeiros.
     */
    @GetMapping("/barbershop/{barbershopId}")
    public ResponseEntity<List<BarberResponseDTO>> getBarbersByBarbershop(
            @PathVariable String barbershopId) {

        List<BarberResponseDTO> barbers = barberService.findBarbersByBarbershopId(barbershopId);

        // Retorna 200 OK mesmo que a lista esteja vazia, mas se houver necessidade
        // de diferenciar, poderíamos usar um 404. Por enquanto, 200 é mais amigável.
        return ResponseEntity.ok(barbers);
    }
}