package br.com.barbearia.api.controller;

import br.com.barbearia.api.dto.BarbershopRequestDTO;
import br.com.barbearia.api.dto.BarbershopResponseDTO;
import br.com.barbearia.api.service.BarbershopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para o catálogo de barbearias.
 * Expõe endpoints para CRUD (Create, Read, Update, Delete) de Barbershops.
 * A extração do userId é feita através do objeto Authentication, injetado pelo Spring Security.
 */
@RestController
@RequestMapping("/barbershops")
@RequiredArgsConstructor
@Slf4j
public class BarbershopController {

    private final BarbershopService barbershopService;

    // --- GET /barbershops : Lista todas as barbearias ---
    @GetMapping
    public ResponseEntity<List<BarbershopResponseDTO>> findAll() {
        log.info("Requisição GET para listar todas as barbearias.");
        List<BarbershopResponseDTO> barbershops = barbershopService.findAll();
        return ResponseEntity.ok(barbershops);
    }

    // --- GET /barbershops/{id} : Busca uma barbearia por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<BarbershopResponseDTO> findById(@PathVariable String id) {
        log.info("Requisição GET para buscar barbearia com ID: {}", id);
        BarbershopResponseDTO dto = barbershopService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // --- POST /barbershops : Cria uma nova barbearia ---
    @PostMapping
    public ResponseEntity<BarbershopResponseDTO> create(
            @Valid @RequestBody BarbershopRequestDTO requestDTO,
            Authentication authentication) { // O objeto Authentication contém o ID do usuário logado

        String ownerId = getUserId(authentication);
        log.info("Requisição POST para criar nova barbearia. OwnerId: {}", ownerId);

        BarbershopResponseDTO createdBarbershop = barbershopService.create(requestDTO, ownerId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBarbershop);
    }

    // --- PUT /barbershops/{id} : Atualiza uma barbearia (apenas pelo proprietário) ---
    @PutMapping("/{id}")
    public ResponseEntity<BarbershopResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody BarbershopRequestDTO requestDTO,
            Authentication authentication) {

        String currentUserId = getUserId(authentication);
        log.info("Requisição PUT para atualizar barbearia ID: {} pelo usuário: {}", id, currentUserId);

        BarbershopResponseDTO updatedBarbershop = barbershopService.update(id, requestDTO, currentUserId);

        return ResponseEntity.ok(updatedBarbershop);
    }

    // --- DELETE /barbershops/{id} : Deleta uma barbearia (apenas pelo proprietário) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            Authentication authentication) {

        String currentUserId = getUserId(authentication);
        log.warn("Requisição DELETE para deletar barbearia ID: {} pelo usuário: {}", id, currentUserId);

        barbershopService.delete(id, currentUserId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Extrai o ID do usuário do objeto Authentication.
     * @param authentication O objeto Authentication fornecido pelo Spring Security.
     * @return O ID do usuário (subject do JWT).
     */
    private String getUserId(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            // Em um ambiente com Spring Security configurado, isso não deve acontecer,
            // mas é um fallback seguro.
            log.error("ID do usuário não pôde ser extraído da autenticação.");
            throw new SecurityException("Autenticação inválida ou ausente.");
        }
        // No nosso setup, o 'name' ou 'principal' do Authentication é o ID do usuário (subject do JWT)
        return authentication.getName();
    }
}