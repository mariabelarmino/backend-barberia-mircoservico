package br.com.barbearia.api.service;

import br.com.barbearia.api.domain.Barbershop;
import br.com.barbearia.api.dto.BarbershopRequestDTO;
import br.com.barbearia.api.dto.BarbershopResponseDTO;
import br.com.barbearia.api.exception.ResourceNotFoundException;
import br.com.barbearia.api.mapper.BarbershopMapper;
import br.com.barbearia.api.repository.BarbershopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócios da Barbearia (Catálogo).
 * Implementa as operações de CRUD e regras de validação.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BarbershopService {

    private final BarbershopRepository barbershopRepository;
    private final BarbershopMapper barbershopMapper;

    // --- Operações de Leitura (READ) ---

    /**
     * Busca todas as barbearias no catálogo.
     * @return Lista de BarbershopResponseDTOs.
     */
    public List<BarbershopResponseDTO> findAll() {
        log.info("Buscando todas as barbearias...");
        List<Barbershop> barbershops = barbershopRepository.findAll();
        // Mapeia a lista de entidades para a lista de DTOs de resposta
        return barbershops.stream()
                .map(barbershopMapper::toDto)
                .toList();
    }

    /**
     * Busca uma barbearia por ID.
     * @param id ID da barbearia.
     * @return BarbershopResponseDTO.
     * @throws ResourceNotFoundException Se a barbearia não for encontrada.
     */
    public BarbershopResponseDTO findById(String id) {
        log.info("Buscando barbearia com ID: {}", id);
        Barbershop barbershop = getBarbershopOrThrowException(id);
        // Mapeia a entidade para o DTO de resposta
        return barbershopMapper.toDto(barbershop);
    }

    // --- Operação de Criação (CREATE) ---

    /**
     * Cria uma nova barbearia no catálogo.
     * @param requestDTO DTO com os dados da nova barbearia.
     * @param ownerId ID do usuário que está criando (o proprietário).
     * @return BarbershopResponseDTO da barbearia criada.
     */
    @Transactional
    public BarbershopResponseDTO create(BarbershopRequestDTO requestDTO, String ownerId) {
        log.info("Criando nova barbearia para o proprietário ID: {}", ownerId);

        // 1. Mapeia o DTO de requisição para a entidade Barbershop
        Barbershop barbershop = barbershopMapper.toEntity(requestDTO);

        // 2. Define os campos automáticos
        barbershop.setOwnerId(ownerId);
        barbershop.setCreatedAt(LocalDateTime.now());
        barbershop.setUpdatedAt(LocalDateTime.now());

        // 3. Salva no banco de dados
        Barbershop savedBarbershop = barbershopRepository.save(barbershop);
        log.info("Barbearia criada com sucesso. ID: {}", savedBarbershop.getId());

        // 4. Mapeia a entidade salva para o DTO de resposta
        return barbershopMapper.toDto(savedBarbershop);
    }

    // --- Operação de Atualização (UPDATE) ---

    /**
     * Atualiza os dados de uma barbearia existente.
     * @param id ID da barbearia a ser atualizada.
     * @param requestDTO DTO com os novos dados.
     * @param currentUserId ID do usuário que está fazendo a requisição.
     * @return BarbershopResponseDTO da barbearia atualizada.
     * @throws ResourceNotFoundException Se a barbearia não for encontrada.
     * @throws SecurityException Se o usuário não for o proprietário.
     */
    @Transactional
    public BarbershopResponseDTO update(String id, BarbershopRequestDTO requestDTO, String currentUserId) {
        log.info("Tentativa de atualização da barbearia ID: {} pelo usuário ID: {}", id, currentUserId);

        // 1. Busca a entidade existente ou lança exceção
        Barbershop existingBarbershop = getBarbershopOrThrowException(id);

        // 2. Regra de Negócio: Garante que apenas o proprietário possa atualizar
        if (!existingBarbershop.getOwnerId().equals(currentUserId)) {
            log.warn("Acesso negado: Usuário {} tentou atualizar a barbearia de outro proprietário.", currentUserId);
            throw new SecurityException("Você não tem permissão para atualizar esta barbearia. Apenas o proprietário pode fazê-lo.");
        }

        // 3. Atualiza os campos do Barbershop
        barbershopMapper.updateEntityFromDto(requestDTO, existingBarbershop);
        existingBarbershop.setUpdatedAt(LocalDateTime.now()); // Atualiza timestamp

        // 4. Salva as alterações
        Barbershop updatedBarbershop = barbershopRepository.save(existingBarbershop);
        log.info("Barbearia ID: {} atualizada com sucesso.", id);

        // 5. Mapeia a entidade atualizada para o DTO de resposta
        return barbershopMapper.toDto(updatedBarbershop);
    }

    // --- Operação de Exclusão (DELETE) ---

    /**
     * Deleta uma barbearia por ID.
     * @param id ID da barbearia a ser deletada.
     * @param currentUserId ID do usuário que está fazendo a requisição.
     * @throws ResourceNotFoundException Se a barbearia não for encontrada.
     * @throws SecurityException Se o usuário não for o proprietário.
     */
    @Transactional
    public void delete(String id, String currentUserId) {
        log.warn("Tentativa de exclusão da barbearia ID: {} pelo usuário ID: {}", id, currentUserId);

        // 1. Busca a entidade existente ou lança exceção
        Barbershop existingBarbershop = getBarbershopOrThrowException(id);

        // 2. Regra de Negócio: Garante que apenas o proprietário possa deletar
        if (!existingBarbershop.getOwnerId().equals(currentUserId)) {
            log.warn("Acesso negado: Usuário {} tentou deletar a barbearia de outro proprietário.", currentUserId);
            throw new SecurityException("Você não tem permissão para deletar esta barbearia. Apenas o proprietário pode fazê-lo.");
        }

        // 3. Deleta a barbearia
        barbershopRepository.deleteById(id);
        log.info("Barbearia ID: {} deletada com sucesso.", id);
    }

    // --- Método Auxiliar ---

    /**
     * Busca uma barbearia por ID ou lança uma ResourceNotFoundException.
     * @param id ID da barbearia.
     * @return Entidade Barbershop.
     * @throws ResourceNotFoundException Se a barbearia não for encontrada.
     */
    private Barbershop getBarbershopOrThrowException(String id) {
        Optional<Barbershop> barbershopOptional = barbershopRepository.findById(id);
        if (barbershopOptional.isEmpty()) {
            throw new ResourceNotFoundException("Barbearia não encontrada com o ID: " + id);
        }
        return barbershopOptional.get();
    }
}