package br.com.barbearia.api.service;

import br.com.barbearia.api.domain.Barber;
import br.com.barbearia.api.dto.BarberResponseDTO;
import br.com.barbearia.api.repository.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio e gerenciamento dos Barbeiros.
 * Inclui o registro de novos barbeiros e consultas de disponibilidade.
 */
@Service
public class BarberService {

    private final BarberRepository barberRepository;

    @Autowired
    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    /**
     * Registra um novo barbeiro no sistema.
     * Este método será usado principalmente por endpoints internos/admins para onboarding.
     * @param barber O objeto Barber a ser salvo.
     * @return O DTO de resposta do barbeiro criado.
     */
    public BarberResponseDTO registerBarber(Barber barber) {
        // [TO-DO] Adicionar validação para garantir que o userId não está duplicado.

        Barber savedBarber = barberRepository.save(barber);
        return mapToDTO(savedBarber);
    }

    /**
     * Busca todos os barbeiros cadastrados.
     * @return Uma lista de DTOs de resposta dos barbeiros.
     */
    public List<BarberResponseDTO> findAllBarbers() {
        return barberRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um barbeiro pelo seu ID no banco de dados.
     * @param id O ID do Mongo do barbeiro.
     * @return Um Optional contendo o DTO de resposta, se encontrado.
     */
    public Optional<BarberResponseDTO> findBarberById(String id) {
        return barberRepository.findById(id)
                .map(this::mapToDTO);
    }

    /**
     * Busca barbeiros que trabalham em uma barbearia específica.
     * @param barbershopId O ID da barbearia.
     * @return Lista de DTOs de resposta dos barbeiros.
     */
    public List<BarberResponseDTO> findBarbersByBarbershopId(String barbershopId) {
        return barberRepository.findByBarbershopIdsContaining(barbershopId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converte a entidade Barber para o DTO de resposta.
     */
    private BarberResponseDTO mapToDTO(Barber barber) {
        return BarberResponseDTO.builder()
                .id(barber.getId())
                .userId(barber.getUserId())
                .name(barber.getName())
                .barbershopIds(barber.getBarbershopIds())
                .bio(barber.getBio())
                .build();
    }
}