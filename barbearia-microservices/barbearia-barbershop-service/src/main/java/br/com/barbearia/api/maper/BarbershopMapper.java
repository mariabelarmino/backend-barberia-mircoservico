package br.com.barbearia.api.mapper;

import br.com.barbearia.api.domain.Barbershop;
import br.com.barbearia.api.dto.BarbershopRequestDTO;
import br.com.barbearia.api.dto.BarbershopResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Componente de mapeamento principal para conversão entre a Entidade Barbershop
 * e seus DTOs de Request e Response.
 */
@Component
@RequiredArgsConstructor
public class BarbershopMapper {

    private final ServiceItemMapper serviceItemMapper;

    /**
     * Converte um DTO de Requisição (BarbershopRequestDTO) para uma Entidade (Barbershop).
     * Este método é usado na criação de uma nova barbearia.
     * @param dto O DTO de requisição.
     * @param ownerId O ID do proprietário (autenticado) da barbearia.
     * @return A entidade Barbershop.
     */
    public Barbershop toEntity(BarbershopRequestDTO dto, String ownerId) {
        return Barbershop.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .description(dto.getDescription())
                .openingTime(dto.getOpeningTime())
                .closingTime(dto.getClosingTime())
                .services(serviceItemMapper.toEntityList(dto.getServices())) // Mapeia a lista de serviços
                .ownerId(ownerId) // Atribui o ID do proprietário
                .build();
    }

    /**
     * Converte uma Entidade (Barbershop) para um DTO de Resposta (BarbershopResponseDTO).
     * @param entity A entidade Barbershop.
     * @return O DTO de resposta.
     */
    public BarbershopResponseDTO toResponseDto(Barbershop entity) {
        return BarbershopResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .description(entity.getDescription())
                .openingTime(entity.getOpeningTime())
                .closingTime(entity.getClosingTime())
                .services(serviceItemMapper.toDtoList(entity.getServices())) // Mapeia a lista de serviços
                .ownerId(entity.getOwnerId())
                .build();
    }

    /**
     * Converte uma lista de Entidades para uma lista de DTOs de Resposta.
     * @param entities Lista de Barbershop.
     * @return Lista de BarbershopResponseDTO.
     */
    public List<BarbershopResponseDTO> toResponseDtoList(List<Barbershop> entities) {
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza os campos de uma entidade Barbershop existente com os dados de um DTO de requisição.
     * Não atualiza o ID nem o ownerId.
     * @param entity A entidade a ser atualizada.
     * @param dto O DTO de requisição com os novos dados.
     */
    public void updateEntityFromDto(Barbershop entity, BarbershopRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setDescription(dto.getDescription());
        entity.setOpeningTime(dto.getOpeningTime());
        entity.setClosingTime(dto.getClosingTime());
        // A lista de serviços é substituída (ou atualizada)
        entity.setServices(serviceItemMapper.toEntityList(dto.getServices()));
    }
}